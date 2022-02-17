package thompson.hexagonal.infrastructure.persistence.inmemory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import thompson.hexagonal.infrastructure.persistence.inmemory.adapter.CustomerInMemoryAdapter;
import thompson.hexagonal.infrastructure.persistence.inmemory.adapter.OrderInMemoryAdapter;
import thompson.hexagonal.infrastructure.persistence.inmemory.adapter.ProductInMemoryAdapter;
import thompson.hexagonal.ports.ICustomerPersistencePort;
import thompson.hexagonal.ports.IOrderPersistencePort;
import thompson.hexagonal.ports.IProductPersistencePort;

@Configuration
public class InMemoryAdapterConfig {
    @Bean("CustomerInMemoryAdapter")
    public ICustomerPersistencePort getCustomerPersistencePort() {
        return new CustomerInMemoryAdapter();
    }

    @Bean("ProductInMemoryAdapter")
    public IProductPersistencePort getProductPersistencePort() {
        return new ProductInMemoryAdapter();
    }

    @Bean("OrderInMemoryAdapter")
    public IOrderPersistencePort getOrderPersistencePort() {
        return new OrderInMemoryAdapter();
    }
}
