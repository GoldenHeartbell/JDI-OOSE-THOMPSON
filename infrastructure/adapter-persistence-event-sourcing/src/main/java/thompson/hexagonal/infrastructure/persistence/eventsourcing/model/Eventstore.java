package thompson.hexagonal.infrastructure.persistence.eventsourcing.model;

import java.util.List;

public interface Eventstore {
    void store(int aggregateId, List<Event> newEvents, int baseVersion);

    List<Event> load(int aggregateId);

    void printEvents();
}
