package dk.statsbiblioteket.user.tra.model;

public interface EventAdder<E extends Event> {
    /**
     * This allows a Collectoin<E> @see{@link java.util.Collection<E>#add(Event)} sub class to implement this method directly.
     *
     * @param event
     * @return
     */
    boolean add(E event);
}
