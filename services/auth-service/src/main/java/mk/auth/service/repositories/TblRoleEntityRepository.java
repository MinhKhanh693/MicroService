package mk.auth.service.repositories;

import mk.auth.service.entities.TblPermissionEntity;
import mk.auth.service.entities.TblRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TblRoleEntityRepository extends JpaRepository<TblRoleEntity, Integer> {
    @Query("SELECT p FROM TblRoleEntity r JOIN TblRoleHasPermissionEntity rp ON r = rp.role JOIN TblPermissionEntity p On p = rp.permission WHERE r.name In (:roleName)")
    List<TblPermissionEntity> findPermissionsByListRoleName(List<String> roleName);
}