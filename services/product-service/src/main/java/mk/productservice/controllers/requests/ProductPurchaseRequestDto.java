package mk.productservice.controllers.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;

/**
 * DTO for {@link mk.productservice.entities.ProductEntity}
 */
public record ProductPurchaseRequestDto(
        @NotNull(message = "Product is mandatory") Integer id,
        @Positive(message = "Product quantity is mandatory") Double quantity
) implements Serializable {
}