package dk.statsbiblioteket.user.tra.apt;

import dk.statsbiblioteket.user.tra.model.Task;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.Boolean.TRUE;

/**
 *
 */
public class MemoryRepositoryTest {

    public static final String E0 = "e0";
    public static final String E1 = "e1";
    public static final String E2 = "e2";
    public static final String E3 = "e3";

    @Test(timeout = 10000L)
    public void simpleFlow() throws Exception {
        MemoryRepository repository = new MemoryRepository();

        Task<MemoryItem, Boolean> task12 = item -> {
            item.add(new MemoryEvent(E1));
            return TRUE;
        };
        Task<MemoryItem, Boolean> task23 = item -> {
            item.add(new MemoryEvent(E2));
            return TRUE;
        };

        repository.add(new MemoryItem(repository, "ITEM1"), new MemoryEvent(E0));
        repository.add(new MemoryItem(repository, "ITEM2"), new MemoryEvent(E0));

        Callable<Boolean> c0_1 = () -> repository.query(new EventQuery(E0)).map(task12).allMatch(b -> b);
        Callable<Boolean> c1_12 = () -> repository.query(new EventQuery(E0, E1)).map(task23).allMatch(b -> b);
        Callable<Boolean> c12_123 = () -> repository.query(new EventQuery(E0, E1, E2)).limit(2).map(e -> TRUE).allMatch(b -> b);

        List<Callable<Boolean>> tasks = Arrays.asList(c1_12, c0_1, c12_123);

        ExecutorService executor = Executors.newCachedThreadPool();

        CompletionService<Boolean> ecs = new ExecutorCompletionService<>(executor);
        List<Future<Boolean>> futures = new ArrayList<Future<Boolean>>(tasks.size());

        tasks.forEach(task -> futures.add(ecs.submit(task)));


        System.out.println("---");

        // when the first task exits, we're done.
        System.out.println("=" + ecs.take().get());

        // tell the rest to finish.
        for (Future<Boolean> future : futures) {
            System.out.println(">" + future.cancel(true));
        }

        // Examine result - for now just be happy :)

    }

}
