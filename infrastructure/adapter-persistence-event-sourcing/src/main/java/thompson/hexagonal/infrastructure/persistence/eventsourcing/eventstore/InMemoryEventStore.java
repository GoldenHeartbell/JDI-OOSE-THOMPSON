package thompson.hexagonal.infrastructure.persistence.eventsourcing.eventstore;

import com.google.common.collect.ImmutableList;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.model.Event;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.model.Eventstore;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.model.OptimisticLockingException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class InMemoryEventStore implements Eventstore {
    private final Map<Integer, List<Event>> eventStore = new ConcurrentHashMap<>();

    @Override
    public void store(int aggregateId, List<Event> newEvents, int baseVersion) {
        eventStore.merge(aggregateId, newEvents, (oldValue, value) -> {
            if (baseVersion != oldValue.get(oldValue.size() - 1).getVersion()) {
                throw new OptimisticLockingException("Base version does not match current stored version");
            }

            return Stream.concat(oldValue.stream(), value.stream()).collect(toList());
        });
    }

    @Override
    public List<Event> load(int aggregateId) {
        return ImmutableList.copyOf(eventStore.getOrDefault(aggregateId, emptyList()));
    }


    public void printEvents() {
        eventStore.entrySet().forEach(entry -> {
            System.out.println("Id: " + entry.getKey() + " ");
            entry.getValue().forEach(event -> {System.out.println(event.toString()); });
        });
    }
}
