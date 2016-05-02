package dk.statsbiblioteket.user.tra.apt;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 *
 */
public class EventQuery {
    Set<String> idSet;

    static AtomicLong globalInstanceCounter = new AtomicLong(0);
    long instanceCounter = globalInstanceCounter.incrementAndGet();

    public EventQuery(String... eventIds) {
        idSet = new HashSet<>(Arrays.asList(eventIds));

    }

    public boolean test(MemoryItem item, Set<MemoryEvent> itemEventSet) {
        Set<String> itemEventIdSet = itemEventSet.stream().map(e -> e.id()).collect(Collectors.toSet());
        //System.out.println(new java.util.Date() + " " + instanceCounter + " " + item.id() + " " + itemEventIdSet + " " + idSet);
        return idSet.equals(itemEventIdSet);
    }
}
