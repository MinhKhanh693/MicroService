package mk.paymentservice.controllers.requests;

import mk.paymentservice.Dtos.CustomerDto;
import mk.paymentservice.enums.PaymentMethodEnum;

import java.math.BigDecimal;

public record PaymentRequestDto(
        Integer id,
        BigDecimal amount,
        PaymentMethodEnum paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerDto customer
) {
}
