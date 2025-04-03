package mk.orderservice.feigns;

import mk.orderservice.controllers.responses.CustomerResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "customer-service", url = "${application.config.customer-service-url}")
public interface CustomerClientFeign {
    @GetMapping("/{customer-id}")
    Optional<CustomerResponseDto> findById(
            @PathVariable("customer-id") String customerId
    );
}
