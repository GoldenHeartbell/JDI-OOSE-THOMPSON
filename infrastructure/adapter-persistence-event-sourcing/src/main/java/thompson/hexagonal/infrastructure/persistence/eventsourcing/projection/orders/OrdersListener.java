package thompson.hexagonal.infrastructure.persistence.eventsourcing.projection.orders;

import com.google.common.eventbus.Subscribe;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.order.OrderAddedEvent;

public class OrdersListener {
    private final OrdersRepository ordersRepository;

    public OrdersListener(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void handle(OrderAddedEvent event) {
        OrderProjection projection = new OrderProjection(event.getAggregateId(), event.getCustomer(), event.getProducts(), event.getVersion());
        ordersRepository.save(projection);
    }
}
