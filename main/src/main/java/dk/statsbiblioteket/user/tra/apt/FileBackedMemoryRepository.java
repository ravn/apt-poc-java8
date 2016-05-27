package dk.statsbiblioteket.user.tra.apt;


import dk.statsbiblioteket.user.tra.model.Event;
import dk.statsbiblioteket.user.tra.model.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class FileBackedMemoryRepository<T extends FileItem, E extends Event> implements Repository<T, E, ItemQuery> {

    private final Path root;
    private final TriFunction<Path, String, Repository<T, E, ItemQuery>, T> itemFactory;

    FileBackedMemoryRepository(Path root, TriFunction<Path, String, Repository<T, E, ItemQuery>, T> itemFactory) {
        this.root = root;
        this.itemFactory = itemFactory;
    }

    Map<T, Set<E>> eventMap = new HashMap<>();

    @Override
    public E put(T item, E event) {
        eventMap.computeIfAbsent(item, key -> new HashSet<>()).add(event);
        return event;
    }

    public T get(String id) {
        return itemFactory.apply(root.resolve(id), id, this);
    }

    public Stream<T> itemStream() {
        try {
            return Files.walk(root)
                    .filter(Files::isRegularFile)
                    .filter(path1 -> path1.getFileName().toString().endsWith(".md5"))
                    .map(path -> itemFactory.apply(path, root.relativize(path).toString(), this));
        } catch (IOException e) {
            throw new RuntimeException("itemStream()", e);
        }
    }

    @Override
    public Stream<T> query(ItemQuery query) {
        // Much easier to keep state with Stream<Stream<...>> which is then flattened.
        Supplier<Stream<T>> itemSupplier = () -> {
            try {
                Thread.sleep(1000 / 10);
            } catch (InterruptedException e) {
                throw new RuntimeException("sleep", e);
            }

            return eventMap.entrySet().stream()
                    .filter(entry -> query.test(entry.getKey(), entry.getValue()))
                    .map(entry -> entry.getKey());
        };

        Stream<Stream<T>> streamStream = Stream.generate(itemSupplier);
        Stream<T> stream = streamStream.flatMap(x -> x);

        return stream;

    }
}
