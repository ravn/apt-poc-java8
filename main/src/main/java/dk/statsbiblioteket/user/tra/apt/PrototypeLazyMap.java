package dk.statsbiblioteket.user.tra.apt;


import java.util.HashMap;
import java.util.function.Function;

/**
 * For now an unoptimized prototype for a Map which generates entries on demand.
 */

public class PrototypeLazyMap<K, V> extends HashMap<K, V> {

    private final Function<K, V> valueCreator;

    PrototypeLazyMap(Function<K, V> valueCreator) {
        this.valueCreator = valueCreator;
    }

    @Override
    public synchronized V get(Object key) {
        if (containsKey(key) == false) {
            // we assume for now key is always a K.
            @SuppressWarnings("unchecked")
            V apply = valueCreator.apply((K) key);
            put((K) key, apply);
        }
        return super.get(key);
    }
}
