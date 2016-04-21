package dk.statsbiblioteket.user.tra.apt;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
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

    @Test(timeout = 60000)
    public void twoParallelFlows() throws InterruptedException {
        Map<Set<String>, LinkedBlockingQueue<Stream<String>>> queueMap;
        queueMap = new AutopopulatingHashMap<>(key -> new LinkedBlockingQueue<>());

        final Set<String> eventSet1 = Collections.unmodifiableSet(new TreeSet<>(Arrays.asList("1")));
        final Set<String> eventSet2 = Collections.unmodifiableSet(new TreeSet<>(Arrays.asList("1", "2")));


        queueMap.get(eventSet1).add(Arrays.asList("1", "2", "3").stream());


        List<List<String>> finalResult = new ArrayList<List<String>>();

        List<Callable<Integer>> runnables = Arrays.asList(
                () -> {
                    //System.err.println("2 ready");
                    List<String> result = queueMap.get(eventSet2).take().flatMap(k -> Stream.of("2>" + k)).collect(Collectors.toList());
                    Set eventSet = new TreeSet<>(eventSet2);
                    eventSet.add("3");
                    queueMap.get(eventSet).add(result.stream());
                    return 0;
                },
                () -> {
                    //System.err.println("1 ready");
                    List<String> result = queueMap.get(eventSet1).take().flatMap(k -> Stream.of("1>" + k)).collect(Collectors.toList());
                    Set eventSet = new TreeSet<>(eventSet1);
                    eventSet.add("2");
                    queueMap.get(eventSet).add(result.stream());
                    return 0;
                });

        // need mechanism to detect if runnable crashes
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<Integer>> z = executor.invokeAll(runnables, 5, TimeUnit.SECONDS);
        // See if any exceptions were thrown.
        for (Future future : z) {
            try {
                future.get();
            } catch (Exception e) {
                throw new RuntimeException(future.toString(), e);  // how to do with thread dump?
            }
        }
        Assert.assertEquals(queueMap.get(new TreeSet<>(Arrays.asList("3", "1", "2"))).take().collect(Collectors.toList()), Arrays.asList("2>1>1", "2>1>2", "2>1>3"));

    }


}