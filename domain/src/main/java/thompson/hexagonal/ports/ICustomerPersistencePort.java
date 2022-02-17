package thompson.hexagonal.ports;

import thompson.hexagonal.model.Customer;

import java.util.List;

public interface ICustomerPersistencePort {
    void addCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    boolean removeCustomer(Integer customerId);
    List<Customer> getCustomers();
    Customer getCustomerById(Integer customerId);
}
