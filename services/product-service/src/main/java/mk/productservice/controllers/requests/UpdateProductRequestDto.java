package mk.productservice.controllers.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link mk.productservice.entities.ProductEntity}
 */
@Value
public class UpdateProductRequestDto implements Serializable {
    Integer id;
    @NotNull
    @Size(max = 255)
    String name;
    String description;
    @NotNull
    Integer availableQuantity;
    @NotNull
    BigDecimal price;
}