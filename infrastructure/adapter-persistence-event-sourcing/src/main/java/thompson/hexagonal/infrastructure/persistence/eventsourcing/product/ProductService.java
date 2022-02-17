package thompson.hexagonal.infrastructure.persistence.eventsourcing.product;

import com.google.common.eventbus.EventBus;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.model.Event;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.model.Eventstore;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.model.OptimisticLockingException;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.utils.Retrier;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static java.util.Collections.singletonList;

public class ProductService {
    private final Eventstore eventStore;
    private final EventBus eventBus;
    private final Retrier conflictRetrier;

    public ProductService(Eventstore eventStore, EventBus eventBus) {
        this.eventStore = eventStore;
        this.eventBus = eventBus;
        int maxAttempts = 3;
        this.conflictRetrier = new Retrier(
                singletonList(OptimisticLockingException.class),
                maxAttempts);
    }

    public Optional<Product> loadProduct(int id) {
        List<Event> eventStream = eventStore.load(id);

        if (eventStream.isEmpty()) {
            return Optional.empty();
        }

        if (eventStream.stream().anyMatch(e -> e.getClass() == ProductRemovedEvent.class)) {
            return Optional.empty();
        }

        return Optional.of(new Product(id, eventStream));
    }

    public void addProduct(thompson.hexagonal.model.Product newProduct) {
        Product product = new Product(newProduct.getProductId(), newProduct.getProductName(), newProduct.getProductPrice(), newProduct.getProductQuantity(), newProduct.getProductDescription());
        storeAndPublishEvents(product);
    }


    public void editProduct(thompson.hexagonal.model.Product product) {
        process(product.getProductId(), originalProduct -> originalProduct.editProduct(product));
    }

    public void removeProduct(thompson.hexagonal.model.Product product) {
        process(product.getProductId(), Product::remove);
    }

    public void getAllProduct() {
        eventStore.printEvents();
    }

    private void process(int productId, Consumer<Product> consumer) {
        conflictRetrier.get(() -> {
            Optional<Product> possibleProduct = loadProduct(productId);
            Product product = possibleProduct.orElseThrow(() -> new ProductNotFoundException(productId));
            consumer.accept(product);
            storeAndPublishEvents(product);
            return product;
        });
    }

    private void storeAndPublishEvents(Product product) {
        eventStore.store(product.getId(), product.getNewEvents(), product.getBaseVersion());
        product.getNewEvents().forEach(eventBus::post);
    }
}
