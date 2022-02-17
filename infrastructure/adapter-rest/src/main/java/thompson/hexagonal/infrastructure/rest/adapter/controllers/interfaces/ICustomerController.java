package thompson.hexagonal.infrastructure.rest.adapter.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thompson.hexagonal.infrastructure.rest.adapter.controllers.implementation_types.CustomerPersistenceImplementation;
import thompson.hexagonal.model.Customer;

import java.util.List;

public interface ICustomerController {
    @GetMapping("/customers")
    ResponseEntity<List<Customer>> getCustomers();

    @PutMapping("/customer")
    ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer);

    @PostMapping("/customer")
    ResponseEntity<Customer> addCustomer(@RequestBody Customer customer);

    @DeleteMapping("/customer/{customerID}")
    ResponseEntity<Void> removeCustomer(@PathVariable Integer customerID);

    @GetMapping("/customer/{customerId}")
    ResponseEntity<Customer> getCustomerById(@PathVariable Integer customerId);

    @PostMapping("/customer/persistence/implementation/{implementation}")
    ResponseEntity<Void> setCustomerPersistenceImplementation(@PathVariable CustomerPersistenceImplementation implementation);
}
