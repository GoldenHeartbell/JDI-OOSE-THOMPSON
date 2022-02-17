package thompson.hexagonal.infrastructure.persistence.inmemory.adapter;

import thompson.hexagonal.model.Customer;
import thompson.hexagonal.ports.ICustomerPersistencePort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerInMemoryAdapter implements ICustomerPersistencePort {
    private static final Map<Integer, Customer> customerMap = new HashMap<>(0);

    @Override
    public void addCustomer(Customer customer) {
        customerMap.put(customer.getCustomerId(), customer);
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        if(customerMap.containsKey(customer.getCustomerId())){
            customerMap.put(customer.getCustomerId(), customer);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeCustomer(Integer customerId) {
        customerMap.remove(customerId);
        return true;
    }

    @Override
    public List<Customer> getCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer getCustomerById(Integer customerId) {
        return customerMap.get(customerId);
    }
}
