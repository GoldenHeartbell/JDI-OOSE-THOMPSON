package thompson.hexagonal.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import thompson.hexagonal.model.Product;
import thompson.hexagonal.ports.IProductPersistencePort;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceAdapterTest {
    private IProductPersistencePort fixture;
    private ProductServiceAdapter sut;

    private Product product;
    private Product newProduct;

    @BeforeEach
    public void setup() {
        final int productId = 1;
        final String productName = "iPhone 18";
        final double productPrice = 500.85;
        final String productDescription = "Een mooie iPhone 18";

        final int newId = 2;
        final String newName = "iPhone 24";
        final double newPrice = 1003.23;
        final String newDescription = "Een zeer goede iPhone 24";

        product = new Product();
        product.setProductId(productId);
        product.setProductName(productName);
        product.setProductPrice(productPrice);
        product.setProductDescription(productDescription);

        newProduct = new Product();
        newProduct.setProductId(newId);
        newProduct.setProductName(newName);
        newProduct.setProductPrice(newPrice);
        newProduct.setProductDescription(newDescription);

        fixture = Mockito.mock(IProductPersistencePort.class);
        sut = new ProductServiceAdapter(fixture);
    }

    @Test
    public void testAddProduct() {
        sut.addProduct(product);
        verify(fixture, times(1)).addProduct(product);
    }

    @Test
    public void testGetProductById() {
        when(fixture.getProductById(product.getProductId())).thenReturn(product);

        Product gottenProduct = sut.getProductById(product.getProductId());
        verify(fixture, times(1)).getProductById(product.getProductId());
        assertEquals(gottenProduct, product);
    }

    @Test
    public void testUpdateProduct() {
        when(fixture.updateProduct(newProduct)).thenReturn(true);
        boolean success = sut.updateProduct(newProduct);

        verify(fixture, times(1)).updateProduct(newProduct);
        assertTrue(success);
    }

    @Test
    public void testUpdateProductNoneExisting() {
        when(fixture.updateProduct(newProduct)).thenReturn(false);
        boolean success = sut.updateProduct(newProduct);

        verify(fixture, times(1)).updateProduct(newProduct);
        assertFalse(success);
    }

    @Test
    public void testRemoveProduct() {
        sut.removeProduct(product.getProductId());
        verify(fixture, times(1)).removeProduct(product.getProductId());
    }
}
