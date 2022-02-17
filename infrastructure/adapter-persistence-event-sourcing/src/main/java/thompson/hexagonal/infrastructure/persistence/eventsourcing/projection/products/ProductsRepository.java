package thompson.hexagonal.infrastructure.persistence.eventsourcing.projection.products;

import java.util.List;

public interface ProductsRepository {
    void save(ProductProjection productProjection);

    List<ProductProjection> listByProduct(int productId);
}
