package thompson.hexagonal.infrastructure.persistence.eventsourcing.model;

import com.google.common.collect.ImmutableList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;
import static java.util.Collections.emptyList;

public class Aggregate {
    private final int id;
    private int baseVersion;
    private final List<Event> newEvents;

    protected Aggregate(int id) {
        this(id, emptyList());
    }

    protected Aggregate(int id, List<Event> eventStream) {
        checkNotNull(eventStream);
        this.id = id;
        eventStream.forEach(e -> {
            apply(e);
            this.baseVersion = e.getVersion();
        });
        this.newEvents = new ArrayList<>();
    }


    protected void applyNewEvent(Event event) {
        checkArgument(event.getVersion() == getNextVersion(),
                "New event version '%s' does not match expected next version '%s'",
                event.getVersion(), getNextVersion());
        apply(event);
        newEvents.add(event);
    }

    private void apply(Event event) {
        try {
            //hier gaat het fout
            Method method = this.getClass().getDeclaredMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        } catch (InvocationTargetException e) {
            Logger.getGlobal().log(Level.SEVERE, e.toString());
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new UnsupportedOperationException(
                    format("Aggregate '%s' doesn't apply event type '%s'", this.getClass(), event.getClass()),
                    e);
        }
    }

    public int getId() {
        return id;
    }

    public int getBaseVersion() {
        return baseVersion;
    }

    public List<Event> getNewEvents() {
        return ImmutableList.copyOf(newEvents);
    }

    protected int getNextVersion() {
        return baseVersion + newEvents.size() + 1;
    }
}
