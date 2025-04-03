package mk.customerservice.services;

import mk.customerservice.entities.Customer;

import java.util.List;

public interface ICustomerService {
    String createCustomer(Customer customer);

    void updateCustomer(Customer newCustomer);

    void deleteCustomer(String id);

    List<Customer> findAllCustomer();

    Customer findCustomerById(String id);

    boolean customerExistsById(String id);
}
