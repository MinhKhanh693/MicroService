package mk.paymentservice.kafka;

import lombok.Builder;
import mk.paymentservice.enums.PaymentMethodEnum;

import java.math.BigDecimal;

@Builder
public record PaymentNotificationRequestDto(
        String orderReference,
        BigDecimal amount,
        PaymentMethodEnum paymentMethod,
        String customerFirstName,
        String customerLastName,
        String customerEmail
) {
}