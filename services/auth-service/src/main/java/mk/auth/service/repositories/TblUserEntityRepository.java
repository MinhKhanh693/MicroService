package mk.auth.service.repositories;

import jakarta.validation.constraints.Size;
import mk.auth.service.entities.TblUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TblUserEntityRepository extends JpaRepository<TblUserEntity, Integer> {
    TblUserEntity findTblUserByUsername(@Size(max = 255) String username);
}