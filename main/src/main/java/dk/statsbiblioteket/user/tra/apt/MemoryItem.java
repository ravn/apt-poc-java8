package dk.statsbiblioteket.user.tra.apt;

import dk.statsbiblioteket.user.tra.model.Event;
import dk.statsbiblioteket.user.tra.model.Id;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MemoryItem implements Id, dk.statsbiblioteket.user.tra.model.EventAdder<Event> {
    private final MemoryRepository repository;
    private String id;

    public MemoryItem(MemoryRepository repository, String id) {
        this.repository = repository;
        this.id = id;
    }

    public void add(MemoryEvent event) {
        repository.put(this, event);
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }

    private List<Event> events = new ArrayList<>();
    @Override
    public boolean add(Event event) {
        events.add(event);
        return true;
    }
}
