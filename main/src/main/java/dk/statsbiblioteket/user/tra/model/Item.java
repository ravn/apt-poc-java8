package dk.statsbiblioteket.user.tra.model;

public interface Item<E extends Event> extends EventAdder<E> {
    // consider inlining.
}
