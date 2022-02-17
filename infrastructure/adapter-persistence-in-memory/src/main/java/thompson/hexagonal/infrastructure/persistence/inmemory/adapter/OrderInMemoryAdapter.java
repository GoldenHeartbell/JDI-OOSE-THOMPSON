package thompson.hexagonal.infrastructure.persistence.inmemory.adapter;

import thompson.hexagonal.model.Order;
import thompson.hexagonal.ports.IOrderPersistencePort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderInMemoryAdapter implements IOrderPersistencePort {
    private static final Map<Integer, Order> orderMap = new HashMap<>(0);

    @Override
    public void addOrder(Order order) {
        orderMap.put(order.getOrderId(), order);
    }

    @Override
    public boolean updateOrder(Order order) {
        final boolean exists = orderMap.containsKey(order.getOrderId());

        if (exists) {
            orderMap.put(order.getOrderId(), order);
        }

        return exists;
    }

    @Override
    public boolean removeOrder(Integer orderId) {
        orderMap.remove(orderId);
        return true;
    }

    @Override
    public List<Order> getOrders() {
        return new ArrayList<>(orderMap.values());
    }

    @Override
    public Order getOrderById(Integer orderId) {
        return orderMap.get(orderId);
    }
}
