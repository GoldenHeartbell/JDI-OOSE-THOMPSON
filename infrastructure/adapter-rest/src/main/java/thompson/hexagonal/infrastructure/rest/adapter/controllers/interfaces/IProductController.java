package thompson.hexagonal.infrastructure.rest.adapter.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thompson.hexagonal.infrastructure.rest.adapter.controllers.implementation_types.ProductPersistenceImplementation;
import thompson.hexagonal.model.Product;

import java.util.List;

public interface IProductController {
    @GetMapping("/products")
    ResponseEntity<List<Product>> getProducts();

    @PutMapping("/product")
    ResponseEntity<Product> updateProduct(@RequestBody Product product);

    @PostMapping("/product")
    ResponseEntity<Product> addProduct(@RequestBody Product product);

    @DeleteMapping("/product/{productID}")
    ResponseEntity<Void> removeProduct(@PathVariable Integer productID);

    @GetMapping("/product/{productID}")
    ResponseEntity<Product> getProductById(@PathVariable Integer productID);

    @PostMapping("/product/persistence/implementation/{implementation}")
    ResponseEntity<Void> setProductPersistenceImplementation(@PathVariable ProductPersistenceImplementation implementation);
}
