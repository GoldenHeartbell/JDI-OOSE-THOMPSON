package thompson.hexagonal.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import thompson.hexagonal.adapter.CustomerServiceAdapter;
import thompson.hexagonal.adapter.OrderServiceAdapter;
import thompson.hexagonal.adapter.ProductServiceAdapter;
import thompson.hexagonal.api.ICustomerService;
import thompson.hexagonal.api.IOrderService;
import thompson.hexagonal.api.IProductService;
import thompson.hexagonal.ports.ICustomerPersistencePort;
import thompson.hexagonal.ports.IOrderPersistencePort;
import thompson.hexagonal.ports.IProductPersistencePort;

@Configuration
public class ApplicationConfiguration {
    // You can change the adapter to use here
    // change the qualifier from CustomerJpaAdapter to CustomerInMemoryAdapter to use the in memory database
    @Bean
    public ICustomerService getCustomerService(@Qualifier("CustomerJpaAdapter") ICustomerPersistencePort customerPersistencePort) {
        return new CustomerServiceAdapter(customerPersistencePort);
    }

    @Bean
    public IProductService getProductService(@Qualifier("ProductJpaAdapter") IProductPersistencePort productPersistencePort) {
        return new ProductServiceAdapter(productPersistencePort);
    }

    @Bean
    public IOrderService getOrderService(@Qualifier("OrderJpaAdapter") IOrderPersistencePort orderPersistencePort) {
        return new OrderServiceAdapter(orderPersistencePort);
    }
}
