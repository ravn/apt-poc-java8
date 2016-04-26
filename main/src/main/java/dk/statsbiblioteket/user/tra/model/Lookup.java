package dk.statsbiblioteket.user.tra.model;

import java.util.stream.Stream;


public interface Lookup<Q, V> {
    /** returns <code>Stream<Stream<V>></code> to make it easy to
     * return one or more V's when ready.     
     * @param query
     * @return
     */
    Stream<Stream<V>> lookup(Q query);
}
