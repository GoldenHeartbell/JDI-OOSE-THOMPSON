package thompson.hexagonal.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import thompson.hexagonal.api.IOrderService;
import thompson.hexagonal.model.Order;
import thompson.hexagonal.ports.IOrderPersistencePort;

import java.util.List;

public class OrderServiceAdapter implements IOrderService {
    private IOrderPersistencePort orderPersistencePort;

    @Autowired
    public OrderServiceAdapter(IOrderPersistencePort orderPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
    }

    @Override
    public void addOrder(Order order) {
        orderPersistencePort.addOrder(order);
    }

    @Override
    public boolean updateOrder(Order order) {
        return orderPersistencePort.updateOrder(order);
    }


    @Override
    public boolean removeOrder(Integer orderId) {
        return orderPersistencePort.removeOrder(orderId);
    }

    @Override
    public List<Order> getOrders() {
        return orderPersistencePort.getOrders();
    }

    @Override
    public Order getOrderById(Integer orderId) {
        return orderPersistencePort.getOrderById(orderId);
    }

    @Override
    public void setPort(IOrderPersistencePort orderPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
    }
}
