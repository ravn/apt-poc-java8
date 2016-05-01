package dk.statsbiblioteket.user.tra.apt;


import dk.statsbiblioteket.user.tra.model.*;
import org.junit.Test;

import java.util.function.Function;
import java.util.stream.Stream;

public class T1 {
    @Test
    public void t1() throws Exception {
        Repository repository = new TestRepository();

        Stream<TestItem> stream = Stream.of(new TestItem(repository, "1"), new TestItem(repository, "2"));

        Function<? super TestItem, ?> f = new TestTask();
        stream.map(f).forEach(System.out::println);
    }

    private class TestItem implements Item, Id, EventAdder<TestEvent> {
        private final Repository repository;
        private final String id;

        public TestItem(Repository repository, String id) {
            this.repository = repository;
            this.id = id;
        }

        @Override
        public String id() {
            return id;
        }

        @Override
        public void add(TestEvent event) {
            repository.add(this, event);
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

    private class TestRepository implements Repository, Lookup<TestQuery, TestItem> {
        @Override
        public void add(Item item, Event event) {
            System.out.println(item + " + " + event);
        }

        @Override
        public Stream<Stream<TestItem>> lookup(TestQuery query) {
            return Stream.of(Stream.of(new TestItem(this, "1")));
        }
    }

    private class TestQuery {}
}
