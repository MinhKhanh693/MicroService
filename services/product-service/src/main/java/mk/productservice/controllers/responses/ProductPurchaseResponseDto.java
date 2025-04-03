package mk.productservice.controllers.responses;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link mk.productservice.entities.ProductEntity}
 */
public record ProductPurchaseResponseDto(Integer id,
                                         @NotNull @Size(max = 255) String name,
                                         String description,
                                         @NotNull BigDecimal price,
                                         double quantity
) implements Serializable {
}