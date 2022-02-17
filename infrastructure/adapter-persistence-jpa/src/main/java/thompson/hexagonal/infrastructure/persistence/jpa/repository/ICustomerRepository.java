package thompson.hexagonal.infrastructure.persistence.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import thompson.hexagonal.infrastructure.persistence.jpa.entity.Customer;

public interface ICustomerRepository extends CrudRepository<Customer, Integer> {
    Customer findByCustomerId(Integer customerId);
}
