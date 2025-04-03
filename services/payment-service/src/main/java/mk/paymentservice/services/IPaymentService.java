package mk.paymentservice.services;

import jakarta.validation.Valid;
import mk.paymentservice.controllers.requests.PaymentRequestDto;
import mk.paymentservice.controllers.responses.PaymentResponseDto;

public interface IPaymentService {

    PaymentResponseDto createPayment(@Valid PaymentRequestDto request);
}
