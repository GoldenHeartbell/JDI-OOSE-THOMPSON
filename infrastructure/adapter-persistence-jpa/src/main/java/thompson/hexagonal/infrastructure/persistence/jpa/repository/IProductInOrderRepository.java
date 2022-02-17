package thompson.hexagonal.infrastructure.persistence.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import thompson.hexagonal.infrastructure.persistence.jpa.entity.ProductInOrder;
import thompson.hexagonal.infrastructure.persistence.jpa.ids.ProductInOrderId;

public interface IProductInOrderRepository extends CrudRepository<ProductInOrder, ProductInOrderId> {
    ProductInOrder findByOrderIdAndProductId(Integer orderId, Integer productId);
}
