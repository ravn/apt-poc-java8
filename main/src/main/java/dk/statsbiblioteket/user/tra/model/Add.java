package dk.statsbiblioteket.user.tra.model;

public interface Add<I extends Item, V> {
    void add(I item, V addValue);
}
