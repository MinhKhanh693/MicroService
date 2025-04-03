package mk.productservice.controllers.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link mk.productservice.entities.CategoryEntity}
 */
@Value
public class CreateCategoryRequestDto implements Serializable {
    Integer id;
    @NotNull
    @Size(max = 100)
    String name;
    String description;
}