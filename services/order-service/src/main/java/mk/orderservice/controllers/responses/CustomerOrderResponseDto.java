package mk.orderservice.controllers.responses;

import mk.orderservice.Enums.PaymentMethodTypeEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO for {@link mk.orderservice.entities.CustomerOrder}
 */
public record CustomerOrderResponseDto(String id, BigDecimal totalAmount, Instant createdDate,
                                       Instant lastModifiedDate, String customerId,
                                       PaymentMethodTypeEnum paymentMethod,
                                       String reference) implements Serializable {
}