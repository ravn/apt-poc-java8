package dk.statsbiblioteket.user.tra.model;

public interface Repository<I extends Item, E extends Event> {

    void add(I item, E event);
}
