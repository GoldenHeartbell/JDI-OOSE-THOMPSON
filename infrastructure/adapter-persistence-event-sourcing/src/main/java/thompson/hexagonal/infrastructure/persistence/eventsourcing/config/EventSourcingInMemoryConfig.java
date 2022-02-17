package thompson.hexagonal.infrastructure.persistence.eventsourcing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.adapter.OrderInMemoryEventStoreAdapter;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.adapter.ProductInMemoryEventStoreAdapter;
import thompson.hexagonal.ports.IOrderPersistencePort;
import thompson.hexagonal.ports.IProductPersistencePort;

@Configuration
public class EventSourcingInMemoryConfig {
    @Bean("ProductInMemoryEventStoreAdapter")
    public IProductPersistencePort getProductPersistencePort() {
        return new ProductInMemoryEventStoreAdapter();
    }

    @Bean("OrderInMemoryEventStoreAdapter")
    public IOrderPersistencePort getOrderPersistencePort() {
        return new OrderInMemoryEventStoreAdapter();
    }
}
