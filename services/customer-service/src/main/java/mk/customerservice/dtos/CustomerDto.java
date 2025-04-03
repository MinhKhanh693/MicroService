package mk.customerservice.dtos;

import lombok.Data;
import mk.customerservice.controllers.requests.CustomerRequest;
import mk.customerservice.controllers.responses.CustomerResponse;
import mk.customerservice.entities.Address;
import mk.customerservice.entities.Customer;

@Data
public class CustomerDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Address address;


    public static CustomerResponse toResponse(Customer customer) {
        if (customer == null) {
            return null;
        }
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());
        response.setEmail(customer.getEmail());
        response.setAddress(customer.getAddress());
        return response;
    }

    public static Customer toEntity(CustomerDto dto) {
        if (dto == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setAddress(dto.getAddress());
        return customer;
    }

    public static Customer toEntity(CustomerRequest request) {
        if (request == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(request.getId());
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setAddress(request.getAddress());
        return customer;
    }
}