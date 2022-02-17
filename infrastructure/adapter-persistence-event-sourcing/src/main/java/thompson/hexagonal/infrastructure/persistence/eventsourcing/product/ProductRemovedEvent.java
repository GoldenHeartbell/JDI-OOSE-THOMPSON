package thompson.hexagonal.infrastructure.persistence.eventsourcing.product;

import thompson.hexagonal.infrastructure.persistence.eventsourcing.model.Event;

import java.time.ZonedDateTime;

public class ProductRemovedEvent extends Event {
    private final int productId;
    private final String name;
    private final double price;
    private final int quantity;
    private final boolean removed;

    protected ProductRemovedEvent(int aggregateId, ZonedDateTime timestamp, int version, int productId, String name, Double price, int quantity) {
        super(aggregateId, timestamp, version);
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.removed = true;
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

    public boolean isRemoved() {
        return removed;
    }

    @Override
    public String toString() {
        return "ProductRemovedEvent {" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", removed=" + removed +
                '}';
    }
}
