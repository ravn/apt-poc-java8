package dk.statsbiblioteket.user.tra.model;

import java.util.function.Function;

/**
 * Actual task the Autonomous Preservation Tool is intended to do.  Is responsible for setting one or more events describing what has been done by this task.  Returns a value suitable for aggregation.
 * <p>
 * Exceptions are expected to be caught and handled.  If not, it indicates a fatal error and the tool should stop.
 */
public interface Task<K, V> extends Function<K, V> {

}
