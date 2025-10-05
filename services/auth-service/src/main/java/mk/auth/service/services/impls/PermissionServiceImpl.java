package mk.auth.service.services.impls;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.auth.service.dtos.TblPermissionEntityDTO;
import mk.auth.service.dtos.requests.CreateTblPermissionEntityRequestDTO;
import mk.auth.service.dtos.requests.UpdateTblPermissionEntityRequestDTO;
import mk.auth.service.dtos.response.TblPermissionEntityResponseDTO;
import mk.auth.service.entities.TblPermissionEntity;
import mk.auth.service.repositories.TblPermissionEntityRepository;
import mk.auth.service.repositories.TblRoleEntityRepository;
import mk.auth.service.services.IPermissionService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "PERMISSION_SERVICE")
public class PermissionServiceImpl implements IPermissionService {

    private final TblRoleEntityRepository roleRepository;
    private final TblPermissionEntityRepository permissionRepository;

    @Override
    @Cacheable(cacheNames = "rolePermissions", keyGenerator = "authoritiesKeyGenerator")
    public List<TblPermissionEntityDTO> getPermissionsByRoleNames(List<String> authorities) {
        if (CollectionUtils.isEmpty(authorities)) {
            log.debug("Authorities list is empty, returning empty permission DTO list.");
            return Collections.emptyList();
        }

        log.info("CACHE MISS - Fetching 'rolePermissions' from DB for authorities key derived from: {}", authorities);
        List<TblPermissionEntity> permissions = this.roleRepository.findPermissionsByListRoleName(authorities);

        return permissions.stream()
                .filter(Objects::nonNull)
                .map(permission -> TblPermissionEntityDTO.builder() // Assume DTO and Builder exist
                        .id(permission.getId())
                        .description(permission.getDescription())
                        .group(permission.getGroup())
                        .method(permission.getMethod())
                        .path(permission.getPath())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "rolePermissions", allEntries = true)
    public TblPermissionEntityResponseDTO createPermission(CreateTblPermissionEntityRequestDTO permissionDTO) {
        if (permissionDTO == null) {
            log.error("Permission DTO is null");
            return null;
        }
        var role = this.roleRepository.findById(permissionDTO.Role_id()).orElseThrow(
                () -> new RuntimeException("Role not found")
        );
        var permission = TblPermissionEntity.builder()
                .description(permissionDTO.description())
                .group(permissionDTO.group())
                .method(permissionDTO.method())
                .path(permissionDTO.path())
                .build();
        permission.addRole(role);

        var permissionSaved = this.permissionRepository.save(permission);
        log.info("Permission with ID {} created successfully", permissionSaved);

        return TblPermissionEntityResponseDTO.builder()
                .id(permissionSaved.getId())
                .description(permissionSaved.getDescription())
                .group(permissionSaved.getGroup())
                .method(permissionSaved.getMethod())
                .path(permissionSaved.getPath())
                .build();
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "rolePermissions", allEntries = true)
    public TblPermissionEntityResponseDTO updatePermission(UpdateTblPermissionEntityRequestDTO permissionDTO) {
        var permission = this.permissionRepository.findById(permissionDTO.id()).orElseThrow(
                () -> new RuntimeException("Permission not found")
        );
        permission.setDescription(permissionDTO.description());
        permission.setGroup(permissionDTO.group());
        permission.setMethod(permissionDTO.method());
        permission.setPath(permissionDTO.path());
        var role = this.roleRepository.findById(permissionDTO.Role_id()).orElseThrow(
                () -> new RuntimeException("Role not found")
        );
        permission.addRole(role);
        var permissionSaved = this.permissionRepository.save(permission);
        log.info("Permission with ID {} updated successfully", permissionSaved);
        return TblPermissionEntityResponseDTO.builder()
                .id(permissionSaved.getId())
                .description(permissionSaved.getDescription())
                .group(permissionSaved.getGroup())
                .method(permissionSaved.getMethod())
                .path(permissionSaved.getPath())
                .build();
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "rolePermissions", allEntries = true)
    public TblPermissionEntityResponseDTO DeletePermission(Integer id) {
        var permission = this.permissionRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Permission not found")
        );
        permission.getRoles().forEach(role -> role.removePermission(permission));
        this.permissionRepository.delete(permission);
        log.info("Permission with ID {} deleted successfully", id);
        return TblPermissionEntityResponseDTO.builder()
                .id(permission.getId())
                .description(permission.getDescription())
                .group(permission.getGroup())
                .method(permission.getMethod())
                .path(permission.getPath())
                .build();
    }
}