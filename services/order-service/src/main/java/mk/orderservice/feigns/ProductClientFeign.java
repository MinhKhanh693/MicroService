package mk.orderservice.feigns;

import mk.orderservice.controllers.requests.ProductPurchaseRequestDto;
import mk.orderservice.controllers.responses.ProductPurchaseResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "product-service", url = "${application.config.product-service-url}")
public interface ProductClientFeign {
    @PostMapping("/purchase")
    Optional<List<ProductPurchaseResponseDto>> purchaseProducts(
            @RequestBody List<ProductPurchaseRequestDto> request
    );
}
