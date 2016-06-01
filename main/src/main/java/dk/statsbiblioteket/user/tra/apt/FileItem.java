package dk.statsbiblioteket.user.tra.apt;

import dk.statsbiblioteket.user.tra.model.Event;
import dk.statsbiblioteket.user.tra.model.Item;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileItem implements Item<Event> {

    protected final Path path;

    public FileItem(Path path) {
        this.path = path;
    }

    private List<Event> events = new ArrayList<>();

    @Override
    public boolean add(Event event) {
        events.add(event);
        return true;
    }

}
