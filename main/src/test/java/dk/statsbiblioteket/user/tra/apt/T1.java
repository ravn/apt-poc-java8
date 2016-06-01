package dk.statsbiblioteket.user.tra.apt;

import dk.statsbiblioteket.user.tra.model.*;
import org.junit.Test;

import java.util.function.Function;
import java.util.stream.Stream;

public class T1 {
    @Test
    public void t1() throws Exception {
        EventAdderValuePutter repository = new TestRepository();

        Stream<TestItem> stream = Stream.of(new TestItem(repository, "1"), new TestItem(repository, "2"));

        Function<? super TestItem, ?> f = new TestTask();
        stream.map(f).forEach(System.out::println);
    }

    private class TestItem implements Id, EventAdder<TestEvent> {
        private final EventAdderValuePutter repository;
        private final String id;

        public TestItem(EventAdderValuePutter repository, String id) {
            this.repository = repository;
            this.id = id;
        }

        @Override
        public String id() {
            return id;
        }

        @Override
        public boolean add(TestEvent event) {
            repository.put(this, event);
            return false;
        }
    }

    private class TestTask implements Function<TestItem, Boolean> {
        @Override
        public Boolean apply(TestItem item) {
            // stuff
            item.add(new TestEvent("e"));
            return Boolean.TRUE;
        }
    }

    private class TestEvent implements Event {
        private final String e;

        public TestEvent(String e) {
            this.e = e;
        }
    }

    private class TestRepository<I extends TestItem, E extends TestEvent, Q> implements Repository<I, E, Q> {
        @Override
        public E put(I item, E event) {
            System.out.println(item + " + " + event);
            return event;
        }

        @Override
        public Stream<I> query(Q query) {
            return null;
        }

//        @Override
        //       public Stream<Stream<TestItem>> query(TestQuery query) {
        //         return Stream.of(Stream.of(new TestItem(this, "1")));
    }
}
