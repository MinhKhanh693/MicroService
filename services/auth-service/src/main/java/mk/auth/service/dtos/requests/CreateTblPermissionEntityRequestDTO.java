package mk.auth.service.dtos.requests;

import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link mk.auth.service.entities.TblPermissionEntity}
 */
public record CreateTblPermissionEntityRequestDTO(@Size(max = 255) String method, @Size(max = 255) String group,
                                                  @Size(max = 255) String description,
                                                  @Size(max = 255) String path,
                                                  Integer Role_id) implements Serializable {
}