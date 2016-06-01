package dk.statsbiblioteket.user.tra.apt;

import dk.statsbiblioteket.user.tra.model.Event;
import dk.statsbiblioteket.user.tra.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class StringItem implements Item<Event> {

    List<Event> events = new ArrayList<>();
    private String s;

    public StringItem(String s) {
        this.s = s;
    }

    @Override
    public boolean add(Event event) {
        events.add(event);
        return true;
    }
}
