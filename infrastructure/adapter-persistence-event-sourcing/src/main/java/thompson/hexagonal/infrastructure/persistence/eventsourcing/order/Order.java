package thompson.hexagonal.infrastructure.persistence.eventsourcing.order;

import thompson.hexagonal.infrastructure.persistence.eventsourcing.model.Aggregate;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.model.Event;
import thompson.hexagonal.model.Customer;
import thompson.hexagonal.model.Product;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order extends Aggregate {
    private Integer orderId;
    private Customer customer;
    private List<Product> products = new ArrayList<>();

    public Order(int id, Customer customer, List<Product> products) {
        super(id);
        OrderAddedEvent orderAddedEvent = new OrderAddedEvent(getId(), ZonedDateTime.now(), getNextVersion(), id, customer, products);
        applyNewEvent(orderAddedEvent);
    }

    public Order(int id, List<Event> eventStream) {
        super(id, eventStream);
    }

    @SuppressWarnings("unused")
    private void apply(OrderAddedEvent event) {
        orderId = event.getOrderId();
        customer = event.getCustomer();
        products = event.getProducts();
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
