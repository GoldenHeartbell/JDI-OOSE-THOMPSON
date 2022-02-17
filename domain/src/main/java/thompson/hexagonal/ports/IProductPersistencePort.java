package thompson.hexagonal.ports;

import thompson.hexagonal.model.Product;

import java.util.List;

public interface IProductPersistencePort {
    Product getProductById(Integer productId);
    void addProduct(Product product);
    List<Product> getProducts();
    boolean updateProduct(Product product);
    boolean removeProduct(Integer productId);
}
