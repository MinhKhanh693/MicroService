package mk.auth.service.dtos.response;

import lombok.Builder;

import java.io.Serializable;

/**
 * DTO for {@link mk.auth.service.entities.TblPermissionEntity}
 */
@Builder
public record TblPermissionEntityResponseDTO(Integer id, String method, String group, String description,
                                             String path) implements Serializable {
}