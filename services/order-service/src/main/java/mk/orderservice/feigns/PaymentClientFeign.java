package mk.orderservice.feigns;

import jakarta.validation.Valid;
import mk.orderservice.controllers.requests.PaymentRequestDto;
import mk.orderservice.controllers.responses.PaymentResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "${application.config.payment-service-url}")
public interface PaymentClientFeign {
    @PostMapping
    PaymentResponseDto createPayment(
            @RequestBody @Valid PaymentRequestDto request
    );
}
