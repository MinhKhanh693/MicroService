package mk.orderservice.kafka;

import lombok.Builder;
import mk.orderservice.Enums.PaymentMethodTypeEnum;
import mk.orderservice.controllers.responses.CustomerResponseDto;
import mk.orderservice.controllers.responses.ProductPurchaseResponseDto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for {@link mk.orderservice.entities.CustomerOrder}
 */
@Builder
public record OrderConfirmation(Integer id, BigDecimal totalAmount,
                                PaymentMethodTypeEnum paymentMethod, String orderReference, CustomerResponseDto customer,
                                List<ProductPurchaseResponseDto> products
) implements Serializable {
}