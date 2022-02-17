package thompson.hexagonal.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import thompson.hexagonal.model.Customer;
import thompson.hexagonal.ports.ICustomerPersistencePort;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceAdapterTest {
    private ICustomerPersistencePort fixture;
    private CustomerServiceAdapter sut;

    private Customer customer;
    private Customer newCustomer;

    @BeforeEach
    public void setup() {
        final int customerId = 1;
        final String customerName = "idiidk";
        final String customerType = "PRSN";
        final String customerAddress = "Apenlaan 243";

        final int newId = 5;
        final String newName = "idiidk v2";
        final String newType = "OBJCT";
        final String newAddress = "Ruitenberglaan 26";

        customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setCustomerName(customerName);
        customer.setCustomerType(customerType);
        customer.setCustomerAddress(customerAddress);

        newCustomer = new Customer();
        newCustomer.setCustomerId(newId);
        newCustomer.setCustomerName(newName);
        newCustomer.setCustomerType(newType);
        newCustomer.setCustomerAddress(newAddress);

        fixture = Mockito.mock(ICustomerPersistencePort.class);
        sut = new CustomerServiceAdapter(fixture);
    }

    @Test
    public void testAddCustomer() {
        sut.addCustomer(customer);
        verify(fixture, times(1)).addCustomer(customer);
    }

    @Test
    public void testGetCustomerById() {
        when(fixture.getCustomerById(customer.getCustomerId())).thenReturn(customer);

        Customer gottenCustomer = sut.getCustomerById(customer.getCustomerId());
        verify(fixture, times(1)).getCustomerById(customer.getCustomerId());
        assertEquals(gottenCustomer, customer);
    }

    @Test
    public void testUpdateCustomer() {
        when(fixture.updateCustomer(newCustomer)).thenReturn(true);
        boolean success = sut.updateCustomer(newCustomer);

        verify(fixture, times(1)).updateCustomer(newCustomer);
        assertTrue(success);
    }

    @Test
    public void testUpdateCustomerNoneExisting() {
        when(fixture.updateCustomer(newCustomer)).thenReturn(false);
        boolean success = sut.updateCustomer(newCustomer);

        verify(fixture, times(1)).updateCustomer(newCustomer);
        assertFalse(success);
    }

    @Test
    public void testRemoveCustomer() {
        sut.removeCustomer(customer.getCustomerId());
        verify(fixture, times(1)).removeCustomer(customer.getCustomerId());
    }
}
