package thompson.hexagonal.infrastructure.rest.adapter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import thompson.hexagonal.adapter.ProductServiceAdapter;
import thompson.hexagonal.infrastructure.persistence.inmemory.adapter.ProductInMemoryAdapter;
import thompson.hexagonal.infrastructure.persistence.jpa.adapter.ProductJpaAdapter;
import thompson.hexagonal.infrastructure.rest.adapter.controllers.ProductController;
import thompson.hexagonal.infrastructure.rest.adapter.controllers.implementation_types.ProductPersistenceImplementation;
import thompson.hexagonal.model.Product;
import thompson.hexagonal.ports.IProductPersistencePort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductControllerTest {
    private ProductController sut;
    private ProductServiceAdapter fixture;
    private Product mockProduct;

    public final int productId = 1;
    public final String productNaam = "Epishce testnaam";
    public final double productPrijs = 21.00;
    public final String productDescription = "Wow wat een geweldige testomschrijving.";

    @BeforeEach
    void setUp() {
        fixture = Mockito.mock(ProductServiceAdapter.class);

        HashMap<String, IProductPersistencePort> portHashMap = new HashMap<>();
        portHashMap.put(ProductPersistenceImplementation.PRODUCT_IN_MEMORY_ADAPTER.getBeanName(), Mockito.mock(ProductInMemoryAdapter.class));
        portHashMap.put(ProductPersistenceImplementation.PRODUCT_JPA_ADAPTER.getBeanName(), Mockito.mock(ProductJpaAdapter.class));
        //TODO: add event store adapters

        sut = new ProductController(fixture, portHashMap);

        mockProduct = new Product();
        mockProduct.setProductId(productId);
        mockProduct.setProductName(productNaam);
        mockProduct.setProductPrice(productPrijs);
        mockProduct.setProductDescription(productDescription);
    }

    @Test
    void testGetAllProductsOK() {
        List<Product> productList = new ArrayList<>();
        productList.add(mockProduct);
        Mockito.when(fixture.getProducts()).thenReturn(productList);

        ResponseEntity<List<Product>> response = sut.getProducts();

        Assertions.assertEquals(response.getBody(), productList);
    }

    @Test
    void testGetProductByIdOK() {
        Mockito.when(fixture.getProductById(mockProduct.getProductId())).thenReturn(mockProduct);

        ResponseEntity<Product> response = sut.getProductById(mockProduct.getProductId());

        Assertions.assertEquals(response.getBody(), mockProduct);
    }

    @Test
    void testUpdateProductOK() {
        Mockito.when(fixture.updateProduct(mockProduct)).thenReturn(true);

        ResponseEntity<Product> response = sut.updateProduct(mockProduct);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateProductBADREQUEST() {
        Mockito.when(fixture.updateProduct(mockProduct)).thenReturn(false);

        ResponseEntity<Product> response = sut.updateProduct(mockProduct);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void addProduct() {
        ResponseEntity<Product> response = sut.addProduct(mockProduct);

        Mockito.verify(fixture).addProduct(mockProduct);

        Assertions.assertEquals(response.getBody(), mockProduct);
    }

    @Test
    void testSetImplementationOkStatus() {
        ResponseEntity<Void> response = sut.setProductPersistenceImplementation(ProductPersistenceImplementation.PRODUCT_IN_MEMORY_ADAPTER);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testSetImplementationBadStatus() {
        ProductPersistenceImplementation implementation = Mockito.mock(ProductPersistenceImplementation.class);
        Mockito.when(implementation.getBeanName()).thenReturn("none");

        ResponseEntity<Void> response = sut.setProductPersistenceImplementation(implementation);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
