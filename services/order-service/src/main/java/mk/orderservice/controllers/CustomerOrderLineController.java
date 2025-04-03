package mk.orderservice.controllers;

import lombok.RequiredArgsConstructor;
import mk.orderservice.controllers.responses.CustomerOrderLineResponseDto;
import mk.orderservice.services.ICustomerOrderLineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-lines")
@RequiredArgsConstructor
public class CustomerOrderLineController {

    private final ICustomerOrderLineService customerOrderLineService;

    @GetMapping("/order/{order-id}")
    public ResponseEntity<List<CustomerOrderLineResponseDto>> findByOrderId(
            @PathVariable("order-id") Integer orderId
    ) {
        return ResponseEntity.ok(this.customerOrderLineService.findAllCustomerOrderLinesByOrderId(orderId));
    }
}
