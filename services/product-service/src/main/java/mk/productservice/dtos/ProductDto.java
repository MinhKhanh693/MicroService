package mk.productservice.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link mk.productservice.entities.ProductEntity}
 */

public record ProductDto(Integer id, @NotNull @Size(max = 255) String name, String description,
                         @NotNull Integer availableQuantity, @NotNull BigDecimal price) implements Serializable {
}