package thompson.hexagonal.infrastructure.persistence.eventsourcing.projection.products;

public class ProductProjection {
    private final int productId;
    private final String name;
    private final double price;
    private final int quantity;
    private final int version;

    public ProductProjection(int productId, String name, double price, int quantity, int version) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.version = version;
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

    public int getVersion() {
        return version;
    }
}
