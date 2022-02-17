package thompson.hexagonal.api;

import thompson.hexagonal.model.Product;
import thompson.hexagonal.ports.IProductPersistencePort;

import java.util.List;

public interface IProductService {
    void addProduct(Product product);

    boolean updateProduct(Product product);

    boolean removeProduct(Integer productId);

    List<Product> getProducts();

    Product getProductById(Integer productId);

    void setPort(IProductPersistencePort productPersistencePort);
}
