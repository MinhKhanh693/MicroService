package mk.gateway.dto.response;

import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;


public record TblUserEntityResponseDto(Integer id, @Size(max = 255) String firstName, @Size(max = 255) String lastName,
                                       LocalDate dateOfBirth, @Size(max = 255) String gender,
                                       @Size(max = 255) String phone, @Size(max = 255) String email,
                                       @Size(max = 255) String username, Instant createdAt,
                                       Instant updatedAt, List<String> authorities) implements Serializable {
}