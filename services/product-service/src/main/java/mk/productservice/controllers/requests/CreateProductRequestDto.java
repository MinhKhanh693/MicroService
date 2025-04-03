package mk.productservice.controllers.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;


public record CreateProductRequestDto(@NotNull @Size(max = 255) String name, String description,
                                      @NotNull Integer availableQuantity,
                                      @NotNull BigDecimal price) implements Serializable {
}