package thompson.hexagonal.infrastructure.persistence.eventsourcing.projection.products;

import com.google.common.eventbus.Subscribe;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.product.ProductAddedEvent;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.product.ProductEditedEvent;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.product.ProductRemovedEvent;

public class ProductsListener {
    private final ProductsRepository productsRepository;

    public ProductsListener(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void handle(ProductAddedEvent event) {
        ProductProjection projection = new ProductProjection(event.getAggregateId(), event.getName(), event.getPrice(), event.getQuantity(), event.getVersion());
        productsRepository.save(projection);
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void handle(ProductEditedEvent event) {
        ProductProjection projection = new ProductProjection(event.getAggregateId(), event.getName(), event.getPrice(), event.getQuantity(), event.getVersion());
        productsRepository.save(projection);
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void handle(ProductRemovedEvent event) {
        ProductProjection projection = new ProductProjection(event.getAggregateId(), event.getName(), event.getPrice(), event.getQuantity(), event.getVersion());
        productsRepository.save(projection);
    }
}
