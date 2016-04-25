package dk.statsbiblioteket.user.tra.model;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Actual task the Autonomous Preservation Tool is intended to do.
 */
public interface Task<K, V> extends Function<K, Stream<V>> {


}
