package mk.notificationservice.kafka.payment;

import java.math.BigDecimal;

public record PaymentConfirmationDto(String orderReference,
                                     BigDecimal amount,
                                     PaymentMethodEnum paymentMethod,
                                     String customerFirstName,
                                     String customerLastName,
                                     String customerEmail) {
}
