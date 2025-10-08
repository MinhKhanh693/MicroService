package mk.user.service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.user.service.dto.TblUserEntityExcelImportDTO;
import mk.user.service.dto.response.TblUserEntityResponseDTO;
import mk.user.service.entity.TblUserEntity;
import mk.user.service.repository.TblUserEntityRepository;
import mk.user.service.service.IExcelService;
import mk.user.service.service.IUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j(topic = "USER_SERVICE")
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final TblUserEntityRepository userRepository;
    private final IExcelService excelService;

    @Override
    public List<TblUserEntityResponseDTO> importListUserFromExcel(InputStream excelFile) throws Exception {
        log.info("Importing user list from Excel");
        List<TblUserEntityExcelImportDTO> importedUsers = this.excelService.readAndMapToObject(excelFile);
        if (importedUsers.isEmpty()) {
            log.warn("No users found in the Excel file");
            throw new Exception("No users found in the Excel file");
        }

        List<TblUserEntity> users = importedUsers.stream()
                .map(user -> {
                    TblUserEntity userEntity = new TblUserEntity();
                    userEntity.setUsername(user.username());
                    userEntity.setPassword(this.passwordEncoder(user.password()));
                    userEntity.setGender(user.gender());
                    userEntity.setEmail(user.email());
                    userEntity.setPhone(user.phone());
                    userEntity.setFirstName(user.firstName());
                    userEntity.setLastName(user.lastName());
                    userEntity.setStatus(user.status());
                    userEntity.setType(user.type());
                    userEntity.setCreatedAt(Instant.now());
                    userEntity.setUpdatedAt(Instant.now());
                    userEntity.setDateOfBirth(user.dateOfBirth());
                    userEntity.setActiveCode(UUID.randomUUID());
                    return userEntity;
                })
                .toList();


        var listUserSaved = userRepository.saveAll(users);
        return listUserSaved.stream()
                .map(user -> new TblUserEntityResponseDTO(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getDateOfBirth(),
                        user.getGender(),
                        user.getPhone(),
                        user.getEmail(),
                        user.getUsername(),
                        user.getUpdatedAt(),
                        user.getCreatedAt(),
                        user.getActiveCode(),
                        user.getStatus(),
                        user.getType()))
                .toList();
    }

    private String passwordEncoder(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
