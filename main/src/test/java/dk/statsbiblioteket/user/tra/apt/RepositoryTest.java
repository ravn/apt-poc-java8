package dk.statsbiblioteket.user.tra.apt;

import dk.statsbiblioteket.user.tra.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 *
 */
public class RepositoryTest {
    EventQuery eq0 = new TestEventQuery();
    EventQuery eq1 = new TestEventQuery("1");
    EventQuery eq2 = new TestEventQuery("1", "2");

    class TestEventQuery<T> implements EventQuery, Predicate<Set<String>> {

        private final TreeSet<T> eventSet;

        public TestEventQuery(T... t) {
            eventSet = new TreeSet<>(Arrays.asList(t));
        }

        @Override
        public boolean test(Set<String> l) {
            return eventSet.equals(l);
        }
    }

    class TestRepository implements Repository, Lookup<EventQuery, TestItem> {
        Map<Item, Set<TestEvent>> eventMap = new HashMap<>();

        public Set<TestEvent> add(Item key) {
            return eventMap.put(key, new HashSet<>());
        }

        public void add(Item key, TestEvent event) {
            eventMap.get(key).add(event);
        }

        public TestItem createItem(String id) {
            TestItem item = new TestItem(id, new HashSet<>());
            return item;
        }

        @Override
        public Stream<Stream<TestItem>> lookup(EventQuery query) {
//            return eventMap.entrySet().stream().filter(e -> e.getValue().stream().map(v -> v.id()).collect(Collectors.toSet()).filter(set)).map(e -> e.getKey());
            return Stream.empty();
        }

        @Override
        public void add(Item item, Event event) {
            // tmp
        }
    }

    class TestItem implements Item, Id {

        private final String id;
        private final Set<Event> events;

        public TestItem(String id, Set<Event> events) {
            this.id = id;
            this.events = events;
        }

        @Override
        public String id() {
            return id;
        }

    }

    class TestEvent implements Event, Id {

        private String id;

        TestEvent(String id) {
            this.id = id;
        }

        @Override
        public String id() {
            return id;
        }
    }

    protected TestRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = new TestRepository();
    }

    // Disabled as task changed. @Test
    public void testTestRepositorySimple() {
        TestItem item = repository.createItem("1");


        List<Item> li = Arrays.asList(item);
        List<Item> el = Collections.emptyList();
        repository.add(item);
        Assert.assertEquals(li, repository.lookup(eq0).collect(toList()));
        Assert.assertEquals(el, repository.lookup(eq1).collect(toList()));
        Assert.assertEquals(el, repository.lookup(eq2).collect(toList()));
        Assert.assertEquals(1, repository.eventMap.entrySet().size());
        repository.add(item, new TestEvent("1"));
        Assert.assertEquals(el, repository.lookup(eq0).collect(toList()));
        Assert.assertEquals(li, repository.lookup(eq1).collect(toList()));
        Assert.assertEquals(el, repository.lookup(eq2).collect(toList()));
        Assert.assertEquals(1, repository.eventMap.entrySet().size());
        repository.add(item, new TestEvent("2"));
        Assert.assertEquals(el, repository.lookup(eq0).collect(toList()));
        Assert.assertEquals(el, repository.lookup(eq1).collect(toList()));
        Assert.assertEquals(li, repository.lookup(eq2).collect(toList()));
        Assert.assertEquals(1, repository.eventMap.entrySet().size());
    }
}
