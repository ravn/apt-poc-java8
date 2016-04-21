package dk.statsbiblioteket.user.tra.apt;


import java.util.HashMap;
import java.util.function.Function;

public class AutopopulatingHashMap<K, V> extends HashMap<K, V> {

    final private Function<K, V> valueCreator;

    AutopopulatingHashMap(Function<K, V> valueCreator) {
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
