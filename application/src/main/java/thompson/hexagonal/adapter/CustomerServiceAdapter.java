package thompson.hexagonal.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import thompson.hexagonal.api.ICustomerService;
import thompson.hexagonal.model.Customer;
import thompson.hexagonal.ports.ICustomerPersistencePort;

import java.util.List;

public class CustomerServiceAdapter implements ICustomerService {
    private ICustomerPersistencePort customerPersistencePort;

    @Autowired
    public CustomerServiceAdapter(ICustomerPersistencePort customerPersistencePort) {
        this.customerPersistencePort = customerPersistencePort;
    }

    @Override
    public void addCustomer(Customer customer) {
        customerPersistencePort.addCustomer(customer);
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return customerPersistencePort.updateCustomer(customer);
    }


    @Override
    public boolean removeCustomer(Integer customerId) {
        return customerPersistencePort.removeCustomer(customerId);
    }

    @Override
    public List<Customer> getCustomers() {
        return customerPersistencePort.getCustomers();
    }

    @Override
    public Customer getCustomerById(Integer customerId) {
        return customerPersistencePort.getCustomerById(customerId);
    }

    @Override
    public void setPort(ICustomerPersistencePort customerPersistencePort) {
        this.customerPersistencePort = customerPersistencePort;
    }
}
