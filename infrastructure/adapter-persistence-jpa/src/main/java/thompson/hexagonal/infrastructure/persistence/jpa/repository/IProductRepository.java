package thompson.hexagonal.infrastructure.persistence.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import thompson.hexagonal.infrastructure.persistence.jpa.entity.Product;

public interface IProductRepository extends CrudRepository<Product, Integer> {
    Product findByProductId(Integer productId);
}
