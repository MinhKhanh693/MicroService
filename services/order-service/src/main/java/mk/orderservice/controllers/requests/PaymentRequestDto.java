package mk.orderservice.controllers.requests;

import lombok.Builder;
import mk.orderservice.Enums.PaymentMethodTypeEnum;
import mk.orderservice.controllers.responses.CustomerResponseDto;

import java.math.BigDecimal;

@Builder
public record PaymentRequestDto(
        Integer id,
        BigDecimal amount,
        PaymentMethodTypeEnum paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponseDto customer
) {
}
