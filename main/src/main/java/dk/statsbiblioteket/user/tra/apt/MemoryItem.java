package dk.statsbiblioteket.user.tra.apt;

import dk.statsbiblioteket.user.tra.model.Id;
import dk.statsbiblioteket.user.tra.model.Item;

/**
 *
 */
public class MemoryItem implements Item, Id {
    private final MemoryRepository repository;
    private String id;

    public MemoryItem(MemoryRepository repository, String id) {
        this.repository = repository;
        this.id = id;
    }

    public void add(MemoryEvent event) {
        repository.add(this, event);
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }
}
