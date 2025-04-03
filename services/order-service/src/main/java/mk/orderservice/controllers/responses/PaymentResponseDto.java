package mk.orderservice.controllers.responses;

import mk.orderservice.Enums.PaymentMethodTypeEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponseDto(Integer id, BigDecimal amount, PaymentMethodTypeEnum paymentMethod, Integer orderId,
                                 LocalDateTime createdDate, LocalDateTime lastModifiedDate) implements Serializable {
}
