package dk.statsbiblioteket.user.tra.model;

import java.util.stream.Stream;

/**
 *
 */
public interface Repository<I extends EventAdder, E, Q> extends //
        EventAdderValuePutter<I, E>, //
        Query<Q, Stream<I>> {
}
