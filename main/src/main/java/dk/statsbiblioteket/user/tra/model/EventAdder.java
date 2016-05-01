package dk.statsbiblioteket.user.tra.model;

public interface EventAdder<E extends Event> {
    void add(E event);
}
