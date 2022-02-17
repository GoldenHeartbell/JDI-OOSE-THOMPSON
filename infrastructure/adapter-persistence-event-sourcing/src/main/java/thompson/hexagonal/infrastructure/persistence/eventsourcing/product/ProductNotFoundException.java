package thompson.hexagonal.infrastructure.persistence.eventsourcing.product;

import static java.lang.String.format;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(int id) {
        super(format("Product met id '%s' kan niet worden gevonden", id));
    }
}
