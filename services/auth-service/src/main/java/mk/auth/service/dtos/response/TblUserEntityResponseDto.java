package mk.auth.service.dtos.response;

import lombok.Builder;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link mk.auth.service.entities.TblUserEntity}
 */
@Builder
public record TblUserEntityResponseDto(Integer id, String firstName, String lastName,
                                       LocalDate dateOfBirth, String gender,
                                       String phone, String email,
                                       String username, Instant createdAt,
                                       Instant updatedAt, List<String> authorities) implements Serializable {
}