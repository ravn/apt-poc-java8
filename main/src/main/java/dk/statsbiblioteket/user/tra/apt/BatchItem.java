package dk.statsbiblioteket.user.tra.apt;

import dk.statsbiblioteket.user.tra.model.Event;
import dk.statsbiblioteket.user.tra.model.Item;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class BatchItem implements Item<Event> {

    private final Path path;

    public BatchItem(Path path) {
        this.path = path;
    }

    public Stream<EditionItem> editionItems() {
        try {
            return Files.list(path)
                    //.peek(o->System.err.println("1)" + o))
                    .filter(Files::isDirectory)
                    //.peek(o->System.err.println("2)" + o))
                    .filter(p -> p.getFileName().toString().matches("^[\\d-]+$"))
                    .peek(o -> System.err.println("3)" + o))
                    .map(EditionItem::new);
        } catch (IOException e) {
            throw new RuntimeException("editionItems()", e);
        }
    }

    private List<Event> events = new ArrayList<>();

    @Override
    public boolean add(Event event) {
        events.add(event);
        return true;
    }

}
