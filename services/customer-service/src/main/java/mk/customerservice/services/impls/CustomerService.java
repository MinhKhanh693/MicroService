package mk.customerservice.services.impls;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.customerservice.entities.Customer;
import mk.customerservice.repositories.CustomerRepository;
import mk.customerservice.services.ICustomerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "CUSTOMER_SERVICE")
public class CustomerService implements ICustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public String createCustomer(Customer customer) {
        Customer CustomerSaved = customerRepository.save(customer);
        log.info("Customer with id: {} created", CustomerSaved.getId());
        return CustomerSaved.getId();
    }

    @Override
    public void updateCustomer(Customer newCustomer) {
        Customer oldCustomer = this.findCustomerById(newCustomer.getId());
        this.mergeCustomer(oldCustomer, newCustomer);
        customerRepository.save(newCustomer);
        log.info("Customer with id: {} updated", newCustomer.getId());
    }

    private void mergeCustomer(Customer oldCustomer, Customer newCustomer) {
        if (StringUtils.isNotBlank(newCustomer.getFirstName())) {
            oldCustomer.setFirstName(newCustomer.getFirstName());
        }
        if (StringUtils.isNotBlank(newCustomer.getEmail())) {
            oldCustomer.setEmail(newCustomer.getEmail());
        }
        if (newCustomer.getEmail() != null) {
            oldCustomer.setAddress(newCustomer.getAddress());
        }
    }

    @Override
    public void deleteCustomer(String id) {
        if (!customerExistsById(id)) {
            throw new RuntimeException("Customer not found, id: " + id);
        }
        customerRepository.deleteById(id);
        log.info("Customer with id: {} deleted", id);
    }

    @Override
    public List<Customer> findAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findCustomerById(String id) {
        var customer = customerRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Customer not found, id: " + id));
        log.info("Customer with id: {} found", id);
        return customer;
    }

    @Override
    public boolean customerExistsById(String id) {
        boolean customer = customerRepository.existsById(id);
        log.info("Customer with id: {} exists: {}", id, customer);
        return customer;
    }
}
