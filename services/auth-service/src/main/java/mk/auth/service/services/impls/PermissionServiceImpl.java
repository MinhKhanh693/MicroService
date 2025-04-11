package mk.auth.service.services.impls;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.auth.service.dtos.TblPermissionEntityDTO;
import mk.auth.service.entities.TblPermissionEntity;
import mk.auth.service.repositories.TblRoleEntityRepository;
import mk.auth.service.services.IPermissionService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PermissionServiceImpl implements IPermissionService {

    private final TblRoleEntityRepository roleRepository; // Repository để truy vấn dữ liệu

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

}