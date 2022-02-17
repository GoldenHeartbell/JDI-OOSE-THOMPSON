package thompson.hexagonal.infrastructure.persistence.inmemory.adapter;

import thompson.hexagonal.model.Product;
import thompson.hexagonal.ports.IProductPersistencePort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductInMemoryAdapter implements IProductPersistencePort {
    private static final Map<Integer, Product> productMap = new HashMap<>(0);

    @Override
    public void addProduct(Product product) {
        productMap.put(product.getProductId(), product);
    }

    @Override
    public boolean updateProduct(Product product) {
        if(productMap.containsKey(product.getProductId())){
            productMap.put(product.getProductId(), product);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeProduct(Integer productId) {
        productMap.remove(productId);
        return true;
    }

    @Override
    public List<Product> getProducts() {
        return new ArrayList<>(productMap.values());
    }

    @Override
    public Product getProductById(Integer productId) {
        return productMap.get(productId);
    }
}
