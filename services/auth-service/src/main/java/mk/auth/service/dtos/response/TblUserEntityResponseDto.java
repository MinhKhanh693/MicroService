package mk.auth.service.dtos.response;

import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link mk.auth.service.entities.TblUserEntity}
 */
@Builder
public record TblUserEntityResponseDto(Integer id, @Size(max = 255) String firstName, @Size(max = 255) String lastName,
                                       LocalDate dateOfBirth, @Size(max = 255) String gender,
                                       @Size(max = 255) String phone, @Size(max = 255) String email,
                                       @Size(max = 255) String username, Instant createdAt,
                                       Instant updatedAt, List<String> authorities) implements Serializable {
}