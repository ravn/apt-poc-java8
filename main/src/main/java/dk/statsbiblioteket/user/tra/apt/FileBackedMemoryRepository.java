package dk.statsbiblioteket.user.tra.apt;


import dk.statsbiblioteket.user.tra.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FileBackedMemoryRepository<T extends FileItem, E extends Event> implements Repository<T, E> {

    private final Path root;
    private final BiFunction<Path, String, T> itemFactory;

    FileBackedMemoryRepository(Path root, BiFunction<Path, String, T> itemFactory) {
        this.root = root;
        this.itemFactory = itemFactory;
    }

    Map<T, Set<E>> eventMap = new HashMap<>();

    @Override
    public void add(T item, E event) {
        eventMap.computeIfAbsent(item, key -> new HashSet<>()).add(event);
    }

    public T get(String id) {
        return itemFactory.apply(root.resolve(id), id);
    }

    public Stream<T> itemStream() {
        try {
            return Files.walk(root)
                    .filter(Files::isRegularFile)
                    .filter(path1->path1.getFileName().toString().endsWith(".md5"))
                    .map(path -> itemFactory.apply(path, root.relativize(path).toString()));
        } catch (IOException e) {
            throw new RuntimeException("itemStream()", e);
        }
    }

}
