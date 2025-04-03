package mk.orderservice.controllers.requests;

import jakarta.validation.constraints.NotNull;
import mk.orderservice.entities.CustomerOrderLine;

import java.io.Serializable;

/**
 * DTO for {@link CustomerOrderLine}
 */
public record CustomerOrderLineRequestDto(Integer id, Integer productId, Integer orderId,
                                          @NotNull Double quantity) implements Serializable {
}