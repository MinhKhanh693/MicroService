package mk.customerservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mk.customerservice.controllers.requests.CustomerRequest;
import mk.customerservice.controllers.responses.CustomerResponse;
import mk.customerservice.dtos.CustomerDto;
import mk.customerservice.entities.Customer;
import mk.customerservice.services.ICustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final ICustomerService iCustomerService;

    @PostMapping
    public ResponseEntity<String> createCustomer(
            @RequestBody @Valid CustomerRequest request
    ) {
        Customer customer = CustomerDto.toEntity(request);
        return ResponseEntity.ok(this.iCustomerService.createCustomer(customer));
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(
            @RequestBody @Valid CustomerRequest request
    ) {
        Customer customer = CustomerDto.toEntity(request);
        this.iCustomerService.updateCustomer(customer);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll() {
        List<CustomerResponse> customers = this.iCustomerService.findAllCustomer()
                .stream()
                .map(CustomerDto::toResponse)
                .toList();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/exists/{customer-id}")
    public ResponseEntity<Boolean> existsById(
            @PathVariable("customer-id") String customerId
    ) {
        return ResponseEntity.ok(this.iCustomerService.customerExistsById(customerId));
    }

    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> findById(
            @PathVariable("customer-id") String customerId
    ) {
        CustomerResponse customerResponse = CustomerDto.toResponse(this.iCustomerService.findCustomerById(customerId));
        return ResponseEntity.ok(customerResponse);
    }

    @DeleteMapping("/{customer-id}")
    public ResponseEntity<Void> delete(
            @PathVariable("customer-id") String customerId
    ) {
        this.iCustomerService.deleteCustomer(customerId);
        return ResponseEntity.accepted().build();
    }

}