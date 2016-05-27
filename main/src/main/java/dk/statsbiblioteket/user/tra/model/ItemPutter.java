package dk.statsbiblioteket.user.tra.model;

public interface ItemPutter<I extends Item, V> {
    /** Deliberately same signature as for {@link java.util.Map#put(java.lang.Object, java.lang.Object)} so a Map implementation
     * can implement this interface directly.
     *
     * @param item
     * @param addValue
     * @return
     */
    V put(I item, V addValue);
}
