package mk.customerservice.controllers.responses;

import lombok.Data;
import mk.customerservice.entities.Address;

@Data
public class CustomerResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Address address;
}
