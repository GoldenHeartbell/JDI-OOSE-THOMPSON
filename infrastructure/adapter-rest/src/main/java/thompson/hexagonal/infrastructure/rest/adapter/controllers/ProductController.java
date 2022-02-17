package thompson.hexagonal.infrastructure.rest.adapter.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thompson.hexagonal.api.IProductService;
import thompson.hexagonal.infrastructure.rest.adapter.controllers.implementation_types.ProductPersistenceImplementation;
import thompson.hexagonal.infrastructure.rest.adapter.controllers.interfaces.IProductController;
import thompson.hexagonal.model.Product;
import thompson.hexagonal.ports.IProductPersistencePort;

import java.util.List;
import java.util.Map;

@RestController
public class ProductController implements IProductController {
    private final IProductService productService;
    private final Map<String, IProductPersistencePort> productPersistencePorts;

    @Autowired
    public ProductController(IProductService productService, Map<String, IProductPersistencePort> productPersistencePorts) {
        this.productService = productService;
        this.productPersistencePorts = productPersistencePorts;
    }

    @RequestMapping(value = "/product", method= RequestMethod.POST)
    @Operation(summary = "Post thompson.hexagonal.product endpoint om een thompson.hexagonal.product toe te voegen", description = "Geef de thompson.hexagonal.product mee in de body om deze toe te voegen")
    @Override
    public ResponseEntity<Product> addProduct(Product product) {
        productService.addProduct(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    @Operation(summary = "Get products endpoint om 1 thompson.hexagonal.product op te halen", description = "Endpoint om een thompson.hexagonal.product uit de database te halen aan de hand van een id")
    @Override
    public ResponseEntity<Product> getProductById(@RequestParam("productID") Integer productId) {
        Product product = productService.getProductById(productId);
        if(product == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(product, HttpStatus.OK);
    }


    @RequestMapping(value = "/products", method= RequestMethod.GET)
    @Operation(summary = "Get thompson.hexagonal.product endpoint om een lijst van alle producten op te halen")
    @Override
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    @RequestMapping(value = "/product", method= RequestMethod.PUT)
    @Operation(summary = "PUT thompson.hexagonal.product endpoint om een thompson.hexagonal.product te updaten", description = "Geef de thompson.hexagonal.product mee in de body om deze te updaten")
    @Override
    public ResponseEntity<Product> updateProduct(Product product) {
        boolean update = productService.updateProduct(product);
        if(!update){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @RequestMapping(value = "/product", method = RequestMethod.DELETE)
    @Operation(summary = "Delete thompson.hexagonal.product endpoint om een thompson.hexagonal.product te verwijderen", description = "Geef het thompson.hexagonal.product mee in de body om deze te verwijderen")
    @Override
    public ResponseEntity<Void> removeProduct(@RequestParam("productID") Integer productId) {
        boolean successfulDelete = productService.removeProduct(productId);
        if (!successfulDelete) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/product/persistence/implementation/{implementation}")
    @Operation(summary = "Endpoint om te wisselen van implementatie", description = "Wisselt de implementatie op runtime door een andere")
    @Override
    public ResponseEntity<Void> setProductPersistenceImplementation(@PathVariable("implementation") ProductPersistenceImplementation implementation) {
        IProductPersistencePort port = this.productPersistencePorts.get(implementation.getBeanName());

        if(port != null) {
            this.productService.setPort(port);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
