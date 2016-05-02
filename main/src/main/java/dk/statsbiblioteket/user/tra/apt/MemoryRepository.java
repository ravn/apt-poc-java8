package dk.statsbiblioteket.user.tra.apt;

import dk.statsbiblioteket.user.tra.model.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 */
public class MemoryRepository implements Repository<MemoryItem, MemoryEvent, EventQuery> {

    Map<MemoryItem, Set<MemoryEvent>> eventMap = new HashMap<>();

    @Override
    public void add(MemoryItem item, MemoryEvent event) {
        eventMap.computeIfAbsent(item, key -> new HashSet<>()).add(event);
    }

    @Override
    public Stream<MemoryItem> query(EventQuery query) {
        // Much easier to keep state with Stream<Stream<...>> which is then flattened.
        Supplier<Stream<MemoryItem>> itemSupplier = () -> {
            try {
                Thread.sleep(1000 / 10);
            } catch (InterruptedException e) {
                throw new RuntimeException("sleep", e);
            }

            return eventMap.entrySet().stream()
                    .filter(entry -> query.test(entry.getKey(), entry.getValue()))
                    .map(entry -> entry.getKey());
        };

        Stream<Stream<MemoryItem>> streamStream = Stream.generate(itemSupplier);
        Stream<MemoryItem> stream = streamStream.flatMap(x -> x);

        return stream;
    }
}
