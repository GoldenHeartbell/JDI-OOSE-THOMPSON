package thompson.hexagonal.infrastructure.persistence.eventsourcing.adapter;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.eventstore.InMemoryEventStore;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.model.Eventstore;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.order.OrderService;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.projection.orders.InMemoryOrdersRepository;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.projection.orders.OrdersListener;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.projection.orders.OrdersRepository;
import thompson.hexagonal.model.Order;
import thompson.hexagonal.ports.IOrderPersistencePort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

public class OrderInMemoryEventStoreAdapter implements IOrderPersistencePort {
    final Eventstore eventstore = new InMemoryEventStore();
    final EventBus eventBus = new AsyncEventBus(newSingleThreadExecutor());
    final OrderService eventOrderService = new OrderService(eventstore, eventBus);


    public OrderInMemoryEventStoreAdapter() {
        OrdersRepository ordersRepository = new InMemoryOrdersRepository();

        eventBus.register(new OrdersListener(ordersRepository));
    }

    @Override
    public void addOrder(Order order) {
        eventOrderService.addOrder(order);
    }

    @Override
    public boolean updateOrder(Order order) {
        return eventOrderService.loadOrder(order.getOrderId()).isPresent();

        // TODO: add update order event
    }

    @Override
    public boolean removeOrder(Integer orderId) {
        // TODO: add remove order event
        return false;
    }

    @Override
    public List<Order> getOrders() {
        //TODO: Create method to get all orders on service
        return new ArrayList<>();
    }

    @Override
    public Order getOrderById(Integer orderId) {
        Optional<thompson.hexagonal.infrastructure.persistence.eventsourcing.order.Order> orderOptional = eventOrderService.loadOrder(orderId);
        if (orderOptional.isEmpty()) {
            return null;
        }

        Order order = new Order();
        order.setOrderId(orderOptional.get().getOrderId());
        order.setProducts(orderOptional.get().getProducts());
        order.setCustomer(orderOptional.get().getCustomer());

        return order;
    }
}
