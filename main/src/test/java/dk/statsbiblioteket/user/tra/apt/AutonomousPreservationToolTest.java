package dk.statsbiblioteket.user.tra.apt;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dk.statsbiblioteket.user.tra.apt.AutonomousPreservationToolTest.F.F1;

/**
 *
 */
public class AutonomousPreservationToolTest {
    @Test
    public void simpleRun() throws Exception {
        Queue<Stream<String>> queue = new LinkedBlockingQueue<Stream<String>>();

        List<String> in = Arrays.asList("1", "2", "3");
        try (Stream<String> stream = in.stream()) {
            @SuppressWarnings("all")
            List<String> result = stream.flatMap(k -> Stream.of(k)).collect(Collectors.toList());
            Assert.assertEquals(in, result);
            queue.add(result.stream()); //System.err.println("<< " + new Date());
        }
    }

    enum F implements AutonomousPreservationToolFunction<String, Stream<String>> {
        F1(s -> s + "1"),
        F2(s -> s + "2");

        // http://stackoverflow.com/a/26665697/53897

        private final Function<String, String> f;

        private F(Function<String, String> f) {
            this.f = f;
        }

        @Override
        public Stream<String> apply(String s) {
            return Stream.of(f.apply(s));
        }
    }

    @Test
    public void simpleEnumParallelRun() {
        Assert.assertEquals("x1", F1.apply("x").findFirst().get());
    }

    @Test
    public void twoParallelFlows() throws InterruptedException {
        LinkedBlockingQueue<Stream<String>> queue1 = new LinkedBlockingQueue<Stream<String>>();
        LinkedBlockingQueue<Stream<String>> queue2 = new LinkedBlockingQueue<Stream<String>>();

        queue1.add(Arrays.asList("1", "2", "3").stream());


        List<List<String>> result2 = new ArrayList<List<String>>();

        List<Callable<Integer>> runnables = Arrays.asList(
                () -> {
                    System.err.println("2 ready");
                    List<String> collect = queue2.take().flatMap(k -> Stream.of("2>" + k)).collect(Collectors.toList());
                    result2.add(collect);
                    System.err.println("collect = " +collect);
                    return 0;
                },
                () -> {
                    System.err.println("1 ready");
                    List<String> result = queue1.take().flatMap(k -> Stream.of("1>" + k)).collect(Collectors.toList());
                    queue2.add(result.stream());
                    System.err.println("result = " +result);
                    return 0;
                });

        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<Integer>> z = executor.invokeAll(runnables);
        Assert.assertEquals(result2.get(0), Arrays.asList("2>1>1", "2>1>2", "2>1>3"));
//
//    List<String> in = Arrays.asList("1", "2", "3");
//    try(
//    Stream<String> stream = in.stream()
//    )
//
//    {
//        @SuppressWarnings("all")
//        List<String> result = stream.flatMap(k -> Stream.of(k)).collect(Collectors.toList());
//        Assert.assertEquals(in, result);
//        queue.add(result.stream()); //System.err.println("<< " + new Date());
//    }
}


}