package thompson.hexagonal.infrastructure.persistence.eventsourcing.projection.orders;

import java.util.List;

public interface OrdersRepository {
    void save (OrderProjection orderProjection);

    List<OrderProjection> listByOrder(int orderId);
}
