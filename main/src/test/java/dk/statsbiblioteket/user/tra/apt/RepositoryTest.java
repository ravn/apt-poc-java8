package dk.statsbiblioteket.user.tra.apt;

import dk.statsbiblioteket.user.tra.model.Event;
import dk.statsbiblioteket.user.tra.model.Item;
import dk.statsbiblioteket.user.tra.model.Repository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;

/**
 *
 */
public class RepositoryTest {
    Set<String> set0 = new TreeSet<>(Arrays.asList());
    Set<String> set1 = new TreeSet<>(Arrays.asList("1"));
    Set<String> set2 = new TreeSet<>(Arrays.asList("1", "2"));

    class TestRepository implements Repository {
        Map<Item, Set<Event>> eventMap = new HashMap<>();

        public Stream<Item> query(Set<String> set) {
            return eventMap.entrySet().stream().filter(e -> e.getValue().equals(set)).map(e -> e.getKey());
        }

        public Set<Event> add(Item key) {
            return eventMap.put(key, new HashSet<>());
        }

        public void add(Item key, Event event) {
            eventMap.get(key).add(event);
        }
    }

    class TestEvent implements Event {

        private String id;

        TestEvent(String id) {
            this.id = id;
        }

        @Override
        public String getId() {
            return id;
        }
    }

    protected TestRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = new TestRepository();
    }

    @Test
    public void test() {
        Item item = new Item(){};

        repository.add(item);
        Assert.assertEquals(1, repository.query(set0).count());
        Assert.assertEquals(0, repository.query(set1).count());
        Assert.assertEquals(0, repository.query(set2).count());
        repository.add(item, new TestEvent("1"));
        Assert.assertEquals(0, repository.query(set0).count());
        Assert.assertEquals(1, repository.query(set1).count());
        Assert.assertEquals(0, repository.query(set2).count());
        repository.add(item, new TestEvent("2"));
        Assert.assertEquals(0, repository.query(set0).count());
        Assert.assertEquals(0, repository.query(set1).count());
        Assert.assertEquals(1, repository.query(set2).count());


    }
}
