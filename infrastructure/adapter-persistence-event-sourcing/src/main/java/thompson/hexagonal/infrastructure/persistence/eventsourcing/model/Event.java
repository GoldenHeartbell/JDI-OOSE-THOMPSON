package thompson.hexagonal.infrastructure.persistence.eventsourcing.model;

import java.time.ZonedDateTime;
import static com.google.common.base.Preconditions.checkNotNull;

public class Event {
    private final int aggregateId;
    private final ZonedDateTime timestamp;
    private final int version;

    protected Event(int aggregateId, ZonedDateTime timestamp, int version) {
        this.aggregateId = aggregateId;
        this.timestamp = checkNotNull(timestamp);
        this.version = version;
    }

    public int getAggregateId() {
        return aggregateId;
    }

    public ZonedDateTime getTimestamp() {
        return this.timestamp;
    }

    public int getVersion() {
        return version;
    }
}
