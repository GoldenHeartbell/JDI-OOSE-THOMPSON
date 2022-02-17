package thompson.hexagonal.infrastructure.persistence.eventsourcing.order;

import static java.lang.String.format;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(int id) {
        super(format("Bestelling met id '%s' kan niet worden gevonden", id));
    }

}
