package mk.user.service.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import mk.user.service.enums.UserStatusEnum;
import mk.user.service.enums.UserTypeEnum;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO for {@link mk.user.service.entity.TblUserEntity}
 */
public record TblUserEntityExcelExportDTO(Integer id, @Size(max = 255) String firstName,
                                          @Size(max = 255) String lastName, LocalDate dateOfBirth,
                                          @Size(max = 255) String gender, @Size(max = 255) String phone,
                                          @Size(max = 255) String email, @Size(max = 255) String username,
                                          Instant updatedAt, Instant createdAt, UUID activeCode, UserStatusEnum status,
                                          UserTypeEnum type) implements Serializable {
}