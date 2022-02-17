package thompson.hexagonal.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import thompson.hexagonal.api.IProductService;
import thompson.hexagonal.model.Product;
import thompson.hexagonal.ports.IProductPersistencePort;

import java.util.List;

public class ProductServiceAdapter implements IProductService {
    private IProductPersistencePort productPersistencePort;

    @Autowired
    public ProductServiceAdapter(IProductPersistencePort productPersistencePort) {
        this.productPersistencePort = productPersistencePort;
    }

    @Override
    public void addProduct(Product product) {
        productPersistencePort.addProduct(product);
    }

    @Override
    public boolean updateProduct(Product updatedProduct) {
        return productPersistencePort.updateProduct(updatedProduct);
    }

    @Override
    public boolean removeProduct(Integer productId) {
        return productPersistencePort.removeProduct(productId);
    }

    @Override
    public List<Product> getProducts() {
        return productPersistencePort.getProducts();
    }

    @Override
    public Product getProductById(Integer productId) {
        return productPersistencePort.getProductById(productId);
    }

    @Override
    public void setPort(IProductPersistencePort productPersistencePort) {
        this.productPersistencePort = productPersistencePort;
    }
}
