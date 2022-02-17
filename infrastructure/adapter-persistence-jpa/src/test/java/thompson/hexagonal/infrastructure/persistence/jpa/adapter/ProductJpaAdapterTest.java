package thompson.hexagonal.infrastructure.persistence.jpa.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import thompson.hexagonal.infrastructure.persistence.jpa.repository.IProductRepository;
import thompson.hexagonal.infrastructure.persistence.jpa.entity.Product;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Assertions;
import java.util.ArrayList;
import java.util.List;

public class ProductJpaAdapterTest {
    private IProductRepository mockProductRepository;
    private ProductJpaAdapter sut;
    private Product mockProductEntity;
    private thompson.hexagonal.model.Product mockProductModel;

    final int productID = 1;
    final String productName = "Test name";
    final double productPrice = 21.99;
    final String productDescription = "Test descriptiones";

    @BeforeEach
    void setup(){
        mockProductRepository = Mockito.mock(IProductRepository.class);

        sut = new ProductJpaAdapter(mockProductRepository);

        mockProductEntity = new Product();
        mockProductModel = new thompson.hexagonal.model.Product();

        mockProductEntity.setProductId(productID);
        mockProductEntity.setProductName(productName);
        mockProductEntity.setProductPrice(productPrice);
        mockProductEntity.setProductDescription(productDescription);

        mockProductModel.setProductId(productID);
        mockProductModel.setProductName(productName);
        mockProductModel.setProductPrice(productPrice);
        mockProductModel.setProductDescription(productDescription);
    }

    @Test
    void addProduct() {
        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);

        sut.addProduct(mockProductModel);

        verify(mockProductRepository, times(1)).save(productArgumentCaptor.capture());
    }

    @Test
    void testGetAllProducts(){
        List<Product> productEntityList = new ArrayList<>();
        productEntityList.add(mockProductEntity);
        Mockito.when(mockProductRepository.findAll()).thenReturn(productEntityList);

        thompson.hexagonal.model.Product returnProductModel = sut.getProducts().get(0);

        Assertions.assertEquals(mockProductModel.getProductId(), returnProductModel.getProductId());
    }

    @Test
    void testUpdateProductProductFound(){
        Mockito.when(mockProductRepository.findByProductId(productID)).thenReturn(mockProductEntity);

        boolean result = sut.updateProduct(mockProductModel);

        Assertions.assertTrue(result);
    }

    @Test
    void testUpdateProductProductNotFound(){
        Mockito.when(mockProductRepository.findByProductId(productID)).thenReturn(null);

        boolean result = sut.updateProduct(mockProductModel);

        Assertions.assertFalse(result);
    }

    @Test
    void testUpdateProductProductHasChanged(){
        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
        Mockito.when(mockProductRepository.findByProductId(productID)).thenReturn(mockProductEntity);

        sut.updateProduct(mockProductModel);
        verify(mockProductRepository).save(productArgumentCaptor.capture());
        Assertions.assertEquals(productID, productArgumentCaptor.getValue().getProductId());
    }

    @Test
    void testRemoveProduct(){
        Mockito.when(mockProductRepository.findByProductId(productID)).thenReturn(mockProductEntity);

        Assertions.assertTrue(sut.removeProduct(1));
    }
    @Test
    void testRemoveProductFalse(){
        Mockito.when(mockProductRepository.findByProductId(productID)).thenReturn(null);

        Assertions.assertFalse(sut.removeProduct(1));
    }
}
