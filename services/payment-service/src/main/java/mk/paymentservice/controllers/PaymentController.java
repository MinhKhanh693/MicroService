package mk.paymentservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mk.paymentservice.controllers.requests.PaymentRequestDto;
import mk.paymentservice.controllers.responses.PaymentResponseDto;
import mk.paymentservice.services.IPaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final IPaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDto> createPayment(
            @RequestBody @Valid PaymentRequestDto request
    ) {
        return ResponseEntity.ok(this.paymentService.createPayment(request));
    }
}