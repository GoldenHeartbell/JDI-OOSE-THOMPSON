package thompson.hexagonal.infrastructure.persistence.eventsourcing.adapter;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.eventstore.InMemoryEventStore;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.model.Eventstore;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.product.ProductService;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.projection.products.InMemoryProductsRepository;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.projection.products.ProductsListener;
import thompson.hexagonal.infrastructure.persistence.eventsourcing.projection.products.ProductsRepository;
import thompson.hexagonal.model.Product;
import thompson.hexagonal.ports.IProductPersistencePort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

public class ProductInMemoryEventStoreAdapter implements IProductPersistencePort {
    final Eventstore eventstore = new InMemoryEventStore();
    final EventBus eventBus = new AsyncEventBus(newSingleThreadExecutor());
    final ProductService eventProductService = new ProductService(eventstore, eventBus);


    public ProductInMemoryEventStoreAdapter() {
        ProductsRepository productsRepository = new InMemoryProductsRepository();

        eventBus.register(new ProductsListener(productsRepository));
    }

    @Override
    public void addProduct(Product product) {
        eventProductService.addProduct(product);
    }

    @Override
    public boolean updateProduct(Product product) {
        if (eventProductService.loadProduct(product.getProductId()).isEmpty()) {
            return false;
        }
        eventProductService.editProduct(product);

        return true;
    }

    @Override
    public boolean removeProduct(Integer productId) {
        Product product = this.getProductById(productId);
        if (product == null) {
            return false;
        }

        eventProductService.removeProduct(product);
        return true;
    }

    @Override
    public List<Product> getProducts() {
        eventProductService.getAllProduct();
        return new ArrayList<>();
    }

    @Override
    public Product getProductById(Integer productId) {
        Optional<thompson.hexagonal.infrastructure.persistence.eventsourcing.product.Product> productOptional = eventProductService.loadProduct(productId);
        if (productOptional.isEmpty()) {
            return null;
        }

        Product product = new Product();
        product.setProductId(productOptional.get().getProductId());
        product.setProductName(productOptional.get().getName());
        product.setProductPrice(productOptional.get().getPrice());
        product.setProductQuantity(productOptional.get().getQuantity());
        product.setProductDescription(productOptional.get().getDescription());

        return product;
    }
}
