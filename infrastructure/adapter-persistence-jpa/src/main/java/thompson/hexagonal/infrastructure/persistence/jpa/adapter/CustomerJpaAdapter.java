package thompson.hexagonal.infrastructure.persistence.jpa.adapter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thompson.hexagonal.infrastructure.persistence.jpa.entity.Customer;
import thompson.hexagonal.infrastructure.persistence.jpa.repository.ICustomerRepository;
import thompson.hexagonal.ports.ICustomerPersistencePort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class CustomerJpaAdapter implements ICustomerPersistencePort {
    private ICustomerRepository customerRepository;

    public CustomerJpaAdapter(ICustomerRepository orderRepository) {
        this.customerRepository = orderRepository;
    }

    @Autowired
    public void setCustomerRepository(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void addCustomer(thompson.hexagonal.model.Customer customer) {
        Customer newCustomer = new Customer();
        newCustomer.setCustomerId(customer.getCustomerId());
        newCustomer.setCustomerName(customer.getCustomerName());
        newCustomer.setCustomerAddress(customer.getCustomerAddress());
        newCustomer.setCustomerType(customer.getCustomerType());
        customerRepository.save(newCustomer);

    }

    @Override
    public boolean updateCustomer(thompson.hexagonal.model.Customer customer) {
        Customer existing = customerRepository.findByCustomerId(customer.getCustomerId());
        if(existing == null) {
            return false;
        }

        existing.setCustomerName(customer.getCustomerName());
        existing.setCustomerAddress(customer.getCustomerAddress());
        existing.setCustomerType(customer.getCustomerType());
        customerRepository.save(existing);

        return true;

    }

    @Override
    public boolean removeCustomer(Integer customerId) {
        Customer existing = customerRepository.findByCustomerId(customerId);
        if(existing == null) {
            return false;
        }

        customerRepository.deleteById(customerId);
        return true;
    }

    @Override
    public List<thompson.hexagonal.model.Customer> getCustomers() {
        List<thompson.hexagonal.model.Customer> customerList = new ArrayList<>();
        List<Customer> customerEntityList = StreamSupport.stream(customerRepository.findAll().spliterator(), false).collect(Collectors.toList());
        for(Customer customerEntity : customerEntityList) {
            thompson.hexagonal.model.Customer customer = new thompson.hexagonal.model.Customer();
            BeanUtils.copyProperties(customerEntity, customer);
            customerList.add(customer);
        }
        return customerList;
    }

    @Override
    public thompson.hexagonal.model.Customer getCustomerById(Integer customerId) {
        Customer customerEntity = customerRepository.findByCustomerId(customerId);
        if(customerEntity == null){
            return null;
        }
        thompson.hexagonal.model.Customer customer = new thompson.hexagonal.model.Customer();
        BeanUtils.copyProperties(customerEntity, customer);
        return customer;
    }
}
