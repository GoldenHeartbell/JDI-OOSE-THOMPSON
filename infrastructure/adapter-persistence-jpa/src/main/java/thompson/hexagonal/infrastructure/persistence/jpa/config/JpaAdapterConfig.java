package thompson.hexagonal.infrastructure.persistence.jpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import thompson.hexagonal.infrastructure.persistence.jpa.adapter.CustomerJpaAdapter;
import thompson.hexagonal.infrastructure.persistence.jpa.adapter.OrderJpaAdapter;
import thompson.hexagonal.infrastructure.persistence.jpa.adapter.ProductJpaAdapter;
import thompson.hexagonal.infrastructure.persistence.jpa.repository.ICustomerRepository;
import thompson.hexagonal.infrastructure.persistence.jpa.repository.IOrderRepository;
import thompson.hexagonal.infrastructure.persistence.jpa.repository.IProductInOrderRepository;
import thompson.hexagonal.infrastructure.persistence.jpa.repository.IProductRepository;
import thompson.hexagonal.ports.ICustomerPersistencePort;
import thompson.hexagonal.ports.IOrderPersistencePort;
import thompson.hexagonal.ports.IProductPersistencePort;

@Configuration
public class JpaAdapterConfig {
    @Bean("CustomerJpaAdapter")
    public ICustomerPersistencePort getCustomerPersistencePort(ICustomerRepository customerRepository) {
        return new CustomerJpaAdapter(customerRepository);
    }

    @Bean("ProductJpaAdapter")
    public IProductPersistencePort getProductPersistencePort(IProductRepository productRepository) {
        return new ProductJpaAdapter(productRepository);
    }

    @Bean("OrderJpaAdapter")
    public IOrderPersistencePort getOrderPersistencePort(IOrderRepository orderRepository, IProductInOrderRepository productInOrderRepository, IProductRepository productRepository) {
        return new OrderJpaAdapter(orderRepository, productInOrderRepository, productRepository);
    }

}
