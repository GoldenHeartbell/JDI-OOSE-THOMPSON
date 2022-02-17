package thompson.hexagonal.infrastructure.persistence.eventsourcing.order;

import com.google.common.eventbus.EventBus;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.model.Event;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.model.Eventstore;

import java.util.List;
import java.util.Optional;

public class OrderService {
    private final Eventstore eventStore;
    private final EventBus eventBus;

    public OrderService(Eventstore eventStore, EventBus eventBus) {
        this.eventStore = eventStore;
        this.eventBus = eventBus;
    }

    public Optional<Order> loadOrder(int id) {
        List<Event> eventStream = eventStore.load(id);

        if (eventStream.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new Order(id, eventStream));
    }

    public void addOrder(thompson.hexagonal.model.Order newOrder) {
        Order order = new Order(newOrder.getOrderId(), newOrder.getCustomer(), newOrder.getProducts());
        storeAndPublishEvents(order);
    }

    private void storeAndPublishEvents(Order order) {
        eventStore.store(order.getId(), order.getNewEvents(), order.getBaseVersion());
        order.getNewEvents().forEach(eventBus::post);
    }
}
