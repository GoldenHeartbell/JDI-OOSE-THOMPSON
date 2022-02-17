package thompson.hexagonal.infrastructure.persistence.jpa.adapter;

import org.springframework.beans.BeanUtils;
import thompson.hexagonal.infrastructure.persistence.jpa.entity.Product;
import thompson.hexagonal.infrastructure.persistence.jpa.repository.IProductRepository;
import thompson.hexagonal.ports.IProductPersistencePort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ProductJpaAdapter implements IProductPersistencePort {
    private final IProductRepository productRepository;

    public ProductJpaAdapter(IProductRepository productRepository){this.productRepository = productRepository;}

    @Override
    public thompson.hexagonal.model.Product getProductById(Integer productId) {
        Product productEntity = productRepository.findByProductId(productId);
        if(productEntity == null){
            return null;
        }
        thompson.hexagonal.model.Product product = new thompson.hexagonal.model.Product();
        BeanUtils.copyProperties(productEntity,product);
        return product;
    }

    @Override
    public void addProduct(thompson.hexagonal.model.Product product) {
        Product newProduct = new Product();
        newProduct.setProductId(product.getProductId());
        newProduct.setProductName(product.getProductName());
        newProduct.setProductPrice(product.getProductPrice());
        newProduct.setProductDescription(product.getProductDescription());
        newProduct.setProductQuantity(product.getProductQuantity());
        productRepository.save(newProduct);
    }

    @Override
    public List<thompson.hexagonal.model.Product> getProducts() {
        List<thompson.hexagonal.model.Product> productList = new ArrayList<>();
        List<Product> productEntityList = StreamSupport.stream(productRepository.findAll().spliterator(), false).collect(Collectors.toList());
        for(Product productEntity: productEntityList){
            thompson.hexagonal.model.Product product = new thompson.hexagonal.model.Product();
            BeanUtils.copyProperties(productEntity, product);
            productList.add(product);
        }

        return productList;
    }

    @Override
    public boolean updateProduct(thompson.hexagonal.model.Product updatedProduct) {
        if(productRepository.findByProductId(updatedProduct.getProductId()) == null) {
            return false;
        }
        else{
            Product newProduct = new Product();
            BeanUtils.copyProperties(updatedProduct,newProduct);
            productRepository.save(newProduct);
            return true;
        }
    }

    @Override
    public boolean removeProduct(Integer productId) {

        Product existing = productRepository.findByProductId(productId);
        if(existing == null) {
            return false;
        }

        productRepository.deleteById(productId);
        return true;
    }
}
