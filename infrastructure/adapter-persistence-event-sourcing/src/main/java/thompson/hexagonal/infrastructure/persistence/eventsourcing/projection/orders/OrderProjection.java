package thompson.hexagonal.infrastructure.persistence.eventsourcing.projection.orders;

import thompson.hexagonal.model.Customer;
import thompson.hexagonal.model.Product;

import java.util.List;

public class OrderProjection {
    private final Integer orderId;
    private final Customer customer;
    private final List<Product> products;
    private final int version;

    public OrderProjection(Integer orderId, Customer customer, List<Product> products, int version) {
        this.orderId = orderId;
        this.customer = customer;
        this.products = products;
        this.version = version;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public thompson.hexagonal.model.Customer getCustomer() {
        return customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public int getVersion() {
        return version;
    }
}
