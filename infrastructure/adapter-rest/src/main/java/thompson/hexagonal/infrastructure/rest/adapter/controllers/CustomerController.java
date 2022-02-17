package thompson.hexagonal.infrastructure.rest.adapter.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thompson.hexagonal.api.ICustomerService;
import thompson.hexagonal.infrastructure.rest.adapter.controllers.implementation_types.CustomerPersistenceImplementation;
import thompson.hexagonal.infrastructure.rest.adapter.controllers.interfaces.ICustomerController;
import thompson.hexagonal.model.Customer;
import thompson.hexagonal.ports.ICustomerPersistencePort;

import java.util.List;
import java.util.Map;

@RestController
public class CustomerController implements ICustomerController {
    private final ICustomerService customerService;
    private final Map<String, ICustomerPersistencePort> customerPersistencePorts;

    @Autowired
    public CustomerController(ICustomerService customerService, Map<String, ICustomerPersistencePort> customerPersistencePorts) {
        this.customerService = customerService;
        this.customerPersistencePorts = customerPersistencePorts;
    }

    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    @Operation(summary = "Post customer endpoint om een customer toe te voegen", description = "Geef de customer mee in de body om deze toe te voegen")
    @Override
    public ResponseEntity<Customer> addCustomer(Customer customer) {
        customerService.addCustomer(customer);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    @Operation(summary = "Get customers endpoint om 1 customer op te halen", description = "Endpoint om een customer uit de database te halen aan de hand van een id")
    @Override
    public ResponseEntity<Customer> getCustomerById(@RequestParam("customerID") Integer customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        if(customer == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }


    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    @Operation(summary = "Get customer endpoint om een lijst van alle customers op te halen")
    @Override
    public ResponseEntity<List<Customer>> getCustomers() {
        return new ResponseEntity<>(customerService.getCustomers(), HttpStatus.OK);
    }


    @RequestMapping(value = "/customer", method = RequestMethod.PUT)
    @Operation(summary = "PUT customer endpoint om een customer toe te updaten", description = "Geef de customer mee in de body om deze te updaten")
    @Override
    public ResponseEntity<Customer> updateCustomer(Customer customer) {
        boolean update = customerService.updateCustomer(customer);
        if (!update) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @RequestMapping(value = "/customer", method = RequestMethod.DELETE)
    @Operation(summary = "Delete customer endpoint om een customer te verwijderen", description = "Geef de customer mee in de body om deze te verwijderen")
    @Override
    public ResponseEntity<Void> removeCustomer(@RequestParam("customerID") Integer customerID) {
        boolean successfulDelete = customerService.removeCustomer(customerID);
        if (!successfulDelete) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/customer/persistence/implementation/{implementation}")
    @Operation(summary = "Endpoint om te wisselen van implementatie", description = "Wisselt de implementatie op runtime door een andere")
    @Override
    public ResponseEntity<Void> setCustomerPersistenceImplementation(@PathVariable("implementation") CustomerPersistenceImplementation implementation) {
        ICustomerPersistencePort port = this.customerPersistencePorts.get(implementation.getBeanName());

        if(port != null) {
            this.customerService.setPort(port);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
