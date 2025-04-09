package mk.paymentservice.controllers.responses;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link mk.paymentservice.entities.PaymentEntity}
 */
@AllArgsConstructor
public class PaymentResponseDto {
    private Integer id;
    private BigDecimal amount;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Integer orderId;

    // Getters and setters
}