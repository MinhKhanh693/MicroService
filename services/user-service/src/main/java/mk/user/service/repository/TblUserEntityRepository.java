package mk.user.service.repository;

import mk.user.service.entity.TblUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TblUserEntityRepository extends JpaRepository<TblUserEntity, Integer> {
}