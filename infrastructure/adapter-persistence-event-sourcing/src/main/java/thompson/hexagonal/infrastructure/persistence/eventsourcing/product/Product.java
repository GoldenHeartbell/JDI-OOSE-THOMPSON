package thompson.hexagonal.infrastructure.persistence.eventsourcing.product;

import thompson.hexagonal.infrastructure.persistence.eventsourcing.model.Aggregate;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.model.Event;

import java.time.ZonedDateTime;
import java.util.List;

public class Product extends Aggregate {
    private int productId;
    private String name;
    private double price;
    private int quantity;
    private String description;
    private boolean removed = false;

    public Product(int id, String name, double price, int quantity, String description) {
        super(id);
        ProductAddedEvent productAddedEvent = new ProductAddedEvent(getId(), ZonedDateTime.now(), getNextVersion(), id, name, price, quantity, description);
        applyNewEvent(productAddedEvent);
    }

    public Product(int id, List<Event> eventStream) {
        super(id, eventStream);
    }

    public void editProduct(thompson.hexagonal.model.Product product) {
        name = product.getProductName();
        price = product.getProductPrice();
        quantity = product.getProductQuantity();
        description = product.getProductDescription();

        ProductEditedEvent productEditedEvent = new ProductEditedEvent(getId(), ZonedDateTime.now(), getNextVersion(), productId, name, price, quantity, description);
        applyNewEvent(productEditedEvent);
    }

    public void remove() {
        removed = true;
        ProductRemovedEvent productRemovedEvent = new ProductRemovedEvent(getId(), ZonedDateTime.now(), getNextVersion(), productId, name, price, quantity);
        applyNewEvent(productRemovedEvent);
    }

    @SuppressWarnings("unused")
    private void apply(ProductAddedEvent event) {
        productId = event.getProductId();
        name = event.getName();
        price = event.getPrice();
        quantity = event.getQuantity();
        description = event.getDescription();
    }

    @SuppressWarnings("unused")
    private void apply(ProductEditedEvent event) {
        productId = event.getProductId();
        name = event.getName();
        price = event.getPrice();
        quantity = event.getQuantity();
        description = event.getDescription();
    }

    @SuppressWarnings("unused")
    private void apply(ProductRemovedEvent event) {
        productId = event.getProductId();
        name = event.getName();
        price = event.getPrice();
        quantity = event.getQuantity();
        removed = event.isRemoved();
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }
}

