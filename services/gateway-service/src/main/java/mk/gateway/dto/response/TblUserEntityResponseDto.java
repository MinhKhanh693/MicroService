package mk.gateway.dto.response;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;


public record TblUserEntityResponseDto(Integer id, String firstName, String lastName,
                                       LocalDate dateOfBirth, String gender,
                                       String phone, String email,
                                       String username, Instant createdAt,
                                       Instant updatedAt, List<String> authorities) implements Serializable {
}