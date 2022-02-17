package thompson.hexagonal.api;

import thompson.hexagonal.model.Customer;
import thompson.hexagonal.ports.ICustomerPersistencePort;

import java.util.List;

public interface ICustomerService {
    void addCustomer(Customer customer);

    boolean updateCustomer(Customer customer);

    boolean removeCustomer(Integer customerId);

    List<Customer> getCustomers();

    Customer getCustomerById(Integer customerId);

    void setPort(ICustomerPersistencePort customerPersistencePort);
}
