package mk.user.service.dto;

import jakarta.validation.constraints.Size;
import mk.user.service.enums.UserStatusEnum;
import mk.user.service.enums.UserTypeEnum;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link mk.user.service.entity.TblUserEntity}
 */
public record TblUserEntityExcelImportDTO(@Size(max = 255) String firstName, @Size(max = 255) String lastName,
                                          LocalDate dateOfBirth, @Size(max = 255) String gender,
                                          @Size(max = 255) String phone, @Size(max = 255) String email,
                                          @Size(max = 255) String username, String password,
                                          UserStatusEnum status, UserTypeEnum type) implements Serializable {
}