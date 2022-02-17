package thompson.hexagonal.ports;

import thompson.hexagonal.model.Order;

import java.util.List;

public interface IOrderPersistencePort {
    void addOrder(Order order);
    List<Order> getOrders();
    Order getOrderById(Integer orderId);
    boolean updateOrder(Order order);
    boolean removeOrder(Integer orderId);
}
