package dk.statsbiblioteket.user.tra.apt;

import dk.statsbiblioteket.user.tra.model.Event;
import dk.statsbiblioteket.user.tra.model.Id;

/**
 *
 */
public class MemoryEvent implements Event, Id {
    private String id;

    public MemoryEvent(String id) {
        this.id = id;
    }

    @Override
    public String id() {
        return id;
    }
}
