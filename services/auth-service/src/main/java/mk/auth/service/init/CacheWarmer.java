package mk.auth.service.init;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.auth.service.entities.TblRoleEntity;
import mk.auth.service.repositories.TblRoleEntityRepository;
import mk.auth.service.services.IPermissionService;
import mk.auth.service.services.impls.AuthenticationServiceImpl;
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
    private final AuthenticationServiceImpl authorizationService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Starting cache warming process for 'rolePermissions'...");
        try {
            // 1. Lấy danh sách tất cả các tên Role từ DB
            // (Cách lấy tùy thuộc vào cấu trúc Role của bạn)
            List<TblRoleEntity> allRoleNames = this.roleRepository.findAll(); // Giả sử có phương thức này

            if (allRoleNames.isEmpty()) {
                log.warn("No role names found to warm up the cache.");
                return;
            }

            // 2. Gọi phương thức @Cacheable cho từng role (hoặc các bộ role phổ biến)
            //    để đưa dữ liệu vào cache.
            //    Ví dụ: Cache cho từng role riêng lẻ
            log.info("Warming cache for individual roles...");

            for (TblRoleEntity roleName : allRoleNames) {
                log.debug("Warming cache for role: {}", roleName);
                // Gọi phương thức cacheable với list chỉ chứa 1 role
                this.permissionService.getPermissionsByRoleNames(List.of(roleName.getName().name()));
            }

            // Bạn cũng có thể làm nóng cache cho các tổ hợp role phổ biến nếu biết trước
            // Ví dụ: authorizationService.getPermissionsByRoleNames(List.of("USER", "EDITOR"));

            log.info("Cache warming process for 'rolePermissions' completed.");

        } catch (Exception e) {
            log.error("Error during cache warming process for 'rolePermissions'", e);
        }
    }

    // Giả sử RoleRepository có phương thức này
    // public List<String> findAllRoleNames() { ... }
}