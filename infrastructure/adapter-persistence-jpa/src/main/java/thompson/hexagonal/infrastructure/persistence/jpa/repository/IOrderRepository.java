package thompson.hexagonal.infrastructure.persistence.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import thompson.hexagonal.infrastructure.persistence.jpa.entity.Order;

public interface IOrderRepository extends CrudRepository<Order, Integer> {
    Order findByOrderId(Integer orderId);
}
