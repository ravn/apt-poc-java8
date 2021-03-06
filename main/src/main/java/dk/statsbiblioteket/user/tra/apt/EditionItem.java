package dk.statsbiblioteket.user.tra.apt;

import dk.statsbiblioteket.user.tra.model.Event;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class EditionItem implements dk.statsbiblioteket.user.tra.model.EventAdder<Event> {
    private final Path path;

    public EditionItem(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    private List<Event> events = new ArrayList<>();

    @Override
    public boolean add(Event event) {
        events.add(event);
        return true;
    }

}
