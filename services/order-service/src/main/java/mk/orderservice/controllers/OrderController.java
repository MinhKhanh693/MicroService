package mk.orderservice.controllers;

import lombok.RequiredArgsConstructor;
import mk.orderservice.controllers.requests.CustomerOrderRequestsDto;
import mk.orderservice.controllers.responses.CustomerOrderResponseDto;
import mk.orderservice.services.ICustomerOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final ICustomerOrderService customerOrderService;

    @PostMapping
    public ResponseEntity<CustomerOrderResponseDto> createOrder(
            @RequestBody CustomerOrderRequestsDto request
    ) {
        return ResponseEntity.ok(this.customerOrderService.createOrder(request));
    }

    @GetMapping
    public ResponseEntity<List<CustomerOrderResponseDto>> findAll() {
        return ResponseEntity.ok(this.customerOrderService.findAll());
    }

    @GetMapping("/{order-id}")
    public ResponseEntity<CustomerOrderResponseDto> findById(
            @PathVariable("order-id") Integer orderId
    ) {
        return ResponseEntity.ok(this.customerOrderService.findById(orderId));
    }
}
