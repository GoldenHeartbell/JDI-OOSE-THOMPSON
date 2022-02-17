package thompson.hexagonal.infrastructure.persistence.eventsourcing.order;

import thompson.hexagonal.infrastructure.persistence.eventsourcing.model.Event;
import thompson.hexagonal.model.Customer;
import thompson.hexagonal.model.Product;

import java.time.ZonedDateTime;
import java.util.List;

public class OrderAddedEvent extends Event {
    private final Integer orderId;
    private final Customer customer;
    private final List<Product> products;

    protected OrderAddedEvent(int aggregateId, ZonedDateTime timestamp, int version, Integer orderId, Customer customer, List<Product> products) {
        super(aggregateId, timestamp, version);

        this.orderId = orderId;
        this.customer = customer;
        this.products = products;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Product> getProducts() {
        return products;
    }
}
