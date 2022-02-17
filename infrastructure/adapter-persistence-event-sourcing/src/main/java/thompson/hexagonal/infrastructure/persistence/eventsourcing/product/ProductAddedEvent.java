package thompson.hexagonal.infrastructure.persistence.eventsourcing.product;

import thompson.hexagonal.infrastructure.persistence.eventsourcing.model.Event;

import java.time.ZonedDateTime;

public class ProductAddedEvent extends Event {
    private final int productId;
    private final String name;
    private final double price;
    private final int quantity;
    private final String description;

    protected ProductAddedEvent(int aggregateId, ZonedDateTime timestamp, int version, int productId, String name, Double price, int quantity, String description) {
        super(aggregateId, timestamp, version);
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }


    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "ProductAddedEvent{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                '}';
    }
}
