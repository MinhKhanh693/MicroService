package mk.auth.service.init;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.auth.service.entities.TblRoleEntity;
import mk.auth.service.repositories.TblRoleEntityRepository;
import mk.auth.service.services.IPermissionService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "CACHE-WARMER")
public class CacheWarmer implements ApplicationRunner {

    private final TblRoleEntityRepository roleRepository;
    private final IPermissionService permissionService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Starting cache warming process for 'rolePermissions'...");
        try {
            List<TblRoleEntity> allRoles = this.roleRepository.findAll(); // Renamed variable

            if (allRoles.isEmpty()) {
                log.warn("No roles found to warm up the cache.");
                return;
            }

            warmCacheForRoles(allRoles); // Extracted method call

            log.info("Cache warming process for 'rolePermissions' completed.");
        } catch (Exception e) {
            log.error("Error during cache warming process for 'rolePermissions'", e);
            // Consider if rethrowing is necessary depending on application requirements
        }
    }

    /**
     * Warms the permission cache for the given list of roles.
     *
     * @param roles The list of TblRoleEntity objects.
     */
    private void warmCacheForRoles(List<TblRoleEntity> roles) {
        log.info("Warming cache for individual roles...");
        for (TblRoleEntity role : roles) {
            if (role.getName() != null) {
                log.debug("Warming cache for role: {}", role.getName());
                // Call the cacheable method with a list containing a single role name
                this.permissionService.getPermissionsByRoleNames(List.of(role.getName().name()));
            } else {
                log.warn("Skipping cache warming for role with ID {} because its name is null.", role.getId());
            }
        }
        // Consider warming cache for common role combinations if needed
        // Example: this.permissionService.getPermissionsByRoleNames(List.of("USER", "EDITOR"));
    }

    // Unused commented-out method removed
}