package thompson.hexagonal.api;

import thompson.hexagonal.model.Order;
import thompson.hexagonal.ports.IOrderPersistencePort;

import java.util.List;

public interface IOrderService {
    void addOrder(Order order);

    boolean updateOrder(Order order);

    boolean removeOrder(Integer orderId);

    List<Order> getOrders();

    Order getOrderById(Integer orderId);

    void setPort(IOrderPersistencePort orderPersistencePort);
}
