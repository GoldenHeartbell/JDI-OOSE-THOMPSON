package thompson.hexagonal.infrastructure.persistence.eventsourcing.projection.orders;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class InMemoryOrdersRepository implements OrdersRepository {
    private final Map<Integer, List<OrderProjection>> orders = new ConcurrentHashMap<>();

    @Override
    public void save(OrderProjection orderProjection) {
        orders.merge(
                orderProjection.getOrderId(),
                newArrayList(orderProjection),
                (oldValue, value) -> Stream.concat(oldValue.stream(), value.stream()).collect(toList())
        );
    }

    @Override
    public List<OrderProjection> listByOrder(int orderId) {
        return orders.getOrDefault(orderId, emptyList()).stream()
                .sorted(comparing(OrderProjection::getVersion))
                .collect(toList());
    }
}
