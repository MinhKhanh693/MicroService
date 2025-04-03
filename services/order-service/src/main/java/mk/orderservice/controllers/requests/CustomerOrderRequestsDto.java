package mk.orderservice.controllers.requests;

import mk.orderservice.Enums.PaymentMethodTypeEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for {@link mk.orderservice.entities.CustomerOrder}
 */
public record CustomerOrderRequestsDto(
        String id,
        BigDecimal amount,
        String customerId,
        PaymentMethodTypeEnum paymentMethod,
        String reference,
        List<ProductPurchaseRequestDto> products
) implements Serializable {
}