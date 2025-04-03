package mk.orderservice.controllers.responses;

import jakarta.validation.constraints.NotNull;
import mk.orderservice.entities.CustomerOrderLine;

import java.io.Serializable;

/**
 * DTO for {@link CustomerOrderLine}
 */
public record CustomerOrderLineResponseDto(Integer id, Integer productId,
                                           @NotNull Double quantity) implements Serializable {
}