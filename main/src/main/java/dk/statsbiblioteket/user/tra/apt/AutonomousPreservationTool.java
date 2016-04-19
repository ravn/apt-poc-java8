package dk.statsbiblioteket.user.tra.apt;

import javax.inject.Inject;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;


public class AutonomousPreservationTool<K, V> implements Runnable {

    private final Stream<K> stream;
    private Function<K, Stream<V>> function;
    private Consumer<V> consumer;

    @Inject
    public AutonomousPreservationTool(Stream<K> stream, Function<K, Stream<V>> function, Consumer<V> consumer) {
        this.stream = stream;
        this.function = function;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        stream.flatMap(function).forEach(consumer);
    }
}
