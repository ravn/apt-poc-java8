package dk.statsbiblioteket.user.tra.apt;

import dk.statsbiblioteket.user.tra.model.Event;
import dk.statsbiblioteket.user.tra.model.Id;
import dk.statsbiblioteket.user.tra.model.Item;
import dk.statsbiblioteket.user.tra.model.Repository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 *
 */
public class RepositoryTest {
    Set<String> set0 = new TreeSet<>(Arrays.asList());
    Set<String> set1 = new TreeSet<>(Arrays.asList("1"));
    Set<String> set2 = new TreeSet<>(Arrays.asList("1", "2"));

    class TestRepository implements Repository {
        Map<Item, Set<TestEvent>> eventMap = new HashMap<>();

        public Stream<Item> query(Set<String> set) {
            return eventMap.entrySet().stream().filter(e -> e.getValue().stream().map(v -> v.getId()).collect(Collectors.toSet()).equals(set)).map(e -> e.getKey());
        }

        public Set<TestEvent> add(Item key) {
            return eventMap.put(key, new HashSet<>());
        }

        public void add(Item key, TestEvent event) {
            eventMap.get(key).add(event);
        }
    }

    class TestEvent implements Event, Id {

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
    public void testTestRepositorySimple() {
        Item item = new Item() {
        };

        List<Item> li = Arrays.asList(item);
        List<Item> el = Collections.emptyList();
        repository.add(item);
        Assert.assertEquals(li, repository.query(set0).collect(toList()));
        Assert.assertEquals(el, repository.query(set1).collect(toList()));
        Assert.assertEquals(el, repository.query(set2).collect(toList()));
        Assert.assertEquals(1, repository.eventMap.entrySet().size());
        repository.add(item, new TestEvent("1"));
        Assert.assertEquals(el, repository.query(set0).collect(toList()));
        Assert.assertEquals(li, repository.query(set1).collect(toList()));
        Assert.assertEquals(el, repository.query(set2).collect(toList()));
        Assert.assertEquals(1, repository.eventMap.entrySet().size());
        repository.add(item, new TestEvent("2"));
        Assert.assertEquals(el, repository.query(set0).collect(toList()));
        Assert.assertEquals(el, repository.query(set1).collect(toList()));
        Assert.assertEquals(li, repository.query(set2).collect(toList()));
        Assert.assertEquals(1, repository.eventMap.entrySet().size());


    }
}
