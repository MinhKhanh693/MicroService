package mk.paymentservice.controllers.responses;

import mk.paymentservice.enums.PaymentMethodEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link mk.paymentservice.entities.PaymentEntity}
 */
public record PaymentResponseDto(Integer id, BigDecimal amount, PaymentMethodEnum paymentMethod, Integer orderId,
                                 LocalDateTime createdDate, LocalDateTime lastModifiedDate) implements Serializable {
}