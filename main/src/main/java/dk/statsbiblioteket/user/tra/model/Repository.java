package dk.statsbiblioteket.user.tra.model;

import java.util.stream.Stream;

/**
 *
 */
public interface Repository<I extends Item, E, Q> extends ItemPutter<I, E>, Query<Q, Stream<I>> {
}
