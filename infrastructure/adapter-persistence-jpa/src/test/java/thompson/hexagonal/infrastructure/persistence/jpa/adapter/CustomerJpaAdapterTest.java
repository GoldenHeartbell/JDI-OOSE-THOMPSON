package thompson.hexagonal.infrastructure.persistence.jpa.adapter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import thompson.hexagonal.infrastructure.persistence.jpa.entity.Customer;
import thompson.hexagonal.infrastructure.persistence.jpa.repository.ICustomerRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


class CustomerJpaAdapterTest {

    private ICustomerRepository mockCustomerRepository;
    private CustomerJpaAdapter sut;
    private Customer mockCustomer;
    private thompson.hexagonal.model.Customer inputCustomer;

    public final int customerId = 1;
    public final String customerName = "Roy";
    public final String customerAdres = "Test adres";
    public final String customerType = "1";


    @BeforeEach
    void setup() {
        mockCustomerRepository = Mockito.mock(ICustomerRepository.class);

        sut = new CustomerJpaAdapter(mockCustomerRepository);

        mockCustomer = new Customer();
        mockCustomer.setCustomerId(customerId);
        mockCustomer.setCustomerName(customerName);
        mockCustomer.setCustomerAddress(customerAdres);
        mockCustomer.setCustomerType(customerType);

        inputCustomer = new thompson.hexagonal.model.Customer();
        inputCustomer.setCustomerId(customerId);
        inputCustomer.setCustomerName(customerName);
        inputCustomer.setCustomerAddress(customerAdres);
        inputCustomer.setCustomerType(customerType);
    }

    @Test
    void updateExistingCustomer() {
        Mockito.when(mockCustomerRepository.findByCustomerId(customerId)).thenReturn(mockCustomer);

        boolean response = sut.updateCustomer(inputCustomer);

        Assertions.assertTrue(response);
    }

    @Test
    void updateNotExistingCustomer() {
        Mockito.when(mockCustomerRepository.findByCustomerId(customerId)).thenReturn(null);

        boolean response = sut.updateCustomer(inputCustomer);

        Assertions.assertFalse(response);
    }
    @Test
    void createNewCustomer(){
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        sut.addCustomer(inputCustomer);

        verify(mockCustomerRepository, times(1)).save(customerArgumentCaptor.capture());//checked save method wordt aangeroepen en of customer wordt meegegeven als argument.
    }

    @Test
    void testRemoveCustomer(){
        Mockito.when(mockCustomerRepository.findByCustomerId(customerId)).thenReturn(mockCustomer);

        Assertions.assertTrue(sut.removeCustomer(1));
    }

    @Test
    void testRemoveCustomerFalse(){
        Mockito.when(mockCustomerRepository.findByCustomerId(customerId)).thenReturn(null);

        Assertions.assertFalse(sut.removeCustomer(1));
    }

    @Test
    void testGetCustomerByID(){
        Mockito.when(mockCustomerRepository.findByCustomerId(customerId)).thenReturn(mockCustomer);

        Assertions.assertEquals(customerName, sut.getCustomerById(customerId).getCustomerName());
    }

    @Test
    void testGetCustomerByIDNotFound(){
        Mockito.when(mockCustomerRepository.findByCustomerId(customerId)).thenReturn(null);

        Assertions.assertNull(sut.getCustomerById(customerId));
    }

    @Test
    void testGetAllCustomers(){
        List<Customer> customerEntityList = new ArrayList<>();
        customerEntityList.add(mockCustomer);
        Mockito.when(mockCustomerRepository.findAll()).thenReturn(customerEntityList);

        thompson.hexagonal.model.Customer returnCustomer = sut.getCustomers().get(0);

        Assertions.assertEquals(inputCustomer.getCustomerId(), returnCustomer.getCustomerId());
    }
}
