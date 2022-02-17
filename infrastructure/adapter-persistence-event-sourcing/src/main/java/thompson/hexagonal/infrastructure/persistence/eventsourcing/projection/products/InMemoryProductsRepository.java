package thompson.hexagonal.infrastructure.persistence.eventsourcing.projection.products;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class InMemoryProductsRepository implements ProductsRepository {
    private final Map<Integer, List<ProductProjection>> productTransactions = new ConcurrentHashMap<>();

    @Override
    public void save(ProductProjection productProjection) {
        productTransactions.merge(
            productProjection.getProductId(),
            newArrayList(productProjection),
            (oldValue, value) -> Stream.concat(oldValue.stream(), value.stream()).collect(toList())
        );
    }

    @Override
    public List<ProductProjection> listByProduct(int productId) {
        return productTransactions.getOrDefault(productId, emptyList()).stream()
                .sorted(comparing(ProductProjection::getVersion))
                .collect(toList());
    }
}
