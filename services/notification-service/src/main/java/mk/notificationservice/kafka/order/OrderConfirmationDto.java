package mk.notificationservice.kafka.order;

import mk.notificationservice.kafka.payment.PaymentMethodEnum;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmationDto(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethodEnum paymentMethod,
        CustomerDto customer,
        List<ProductDto> products
) {
}
