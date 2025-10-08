package mk.auth.service.repositories;

import mk.auth.service.dtos.response.TblPermissionEntityResponseDTO;
import mk.auth.service.entities.TblPermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TblPermissionEntityRepository extends JpaRepository<TblPermissionEntity, Integer> {
    @Query("SELECT new mk.auth.service.dtos.response.TblPermissionEntityResponseDTO(p.id, p.method, p.group, p.description, p.path) FROM TblPermissionEntity p WHERE p.id = :id")
    Optional<TblPermissionEntityResponseDTO> findByIdTblPermissionEntityResponseDTO(Integer id);
}