package thompson.hexagonal.infrastructure.rest.adapter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import thompson.hexagonal.adapter.CustomerServiceAdapter;
import thompson.hexagonal.infrastructure.persistence.inmemory.adapter.CustomerInMemoryAdapter;
import thompson.hexagonal.infrastructure.persistence.jpa.adapter.CustomerJpaAdapter;
import thompson.hexagonal.infrastructure.rest.adapter.controllers.CustomerController;
import thompson.hexagonal.infrastructure.rest.adapter.controllers.implementation_types.CustomerPersistenceImplementation;
import thompson.hexagonal.model.Customer;
import thompson.hexagonal.ports.ICustomerPersistencePort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class CustomerControllerTest {
    private CustomerController sut;
    private CustomerServiceAdapter fixture;
    private Customer mockCustomer;

    public final int customerId = 1;
    public final String customerName = "Geurian";
    public final String customerAdres = "Test adres";
    public final String customerType = "1";

    @BeforeEach
    void setup() {
        fixture = Mockito.mock(CustomerServiceAdapter.class);

        HashMap<String, ICustomerPersistencePort> portHashMap = new HashMap<>();
        portHashMap.put(CustomerPersistenceImplementation.CUSTOMER_IN_MEMORY_ADAPTER.getBeanName(), Mockito.mock(CustomerInMemoryAdapter.class));
        portHashMap.put(CustomerPersistenceImplementation.CUSTOMER_JPA_ADAPTER.getBeanName(), Mockito.mock(CustomerJpaAdapter.class));

        sut = new CustomerController(fixture, portHashMap);

        mockCustomer = new Customer();
        mockCustomer.setCustomerId(customerId);
        mockCustomer.setCustomerName(customerName);
        mockCustomer.setCustomerAddress(customerAdres);
        mockCustomer.setCustomerType(customerType);
    }


    @Test
    void testGetAllCustomersOK() {
        List<Customer> customerList = new ArrayList<>();
        customerList.add(mockCustomer);
        Mockito.when(fixture.getCustomers()).thenReturn(customerList);

        ResponseEntity<List<Customer>> response = sut.getCustomers();

        Assertions.assertEquals(response.getBody(), customerList);
    }

    @Test
    void testGetCustomerByIdOK() {
        Mockito.when(fixture.getCustomerById(mockCustomer.getCustomerId())).thenReturn(mockCustomer);

        ResponseEntity<Customer> response = sut.getCustomerById(mockCustomer.getCustomerId());

        Assertions.assertEquals(response.getBody(), mockCustomer);
    }

    @Test
    void testUpdateCustomerOK() {
        Mockito.when(fixture.updateCustomer(mockCustomer)).thenReturn(true);

        ResponseEntity<Customer> response = sut.updateCustomer(mockCustomer);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateCustomerBADREQUEST() {
        Mockito.when(fixture.updateCustomer(mockCustomer)).thenReturn(false);

        ResponseEntity<Customer> response = sut.updateCustomer(mockCustomer);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void addCustomer() {
        ResponseEntity<Customer> response = sut.addCustomer(mockCustomer);

        Mockito.verify(fixture).addCustomer(mockCustomer);

        Assertions.assertEquals(response.getBody(), mockCustomer);
    }

    @Test
    void testSetImplementationOkStatus() {
        ResponseEntity<Void> response = sut.setCustomerPersistenceImplementation(CustomerPersistenceImplementation.CUSTOMER_IN_MEMORY_ADAPTER);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testSetImplementationBadStatus() {
        CustomerPersistenceImplementation implementation = Mockito.mock(CustomerPersistenceImplementation.class);
        Mockito.when(implementation.getBeanName()).thenReturn("none");

        ResponseEntity<Void> response = sut.setCustomerPersistenceImplementation(implementation);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
