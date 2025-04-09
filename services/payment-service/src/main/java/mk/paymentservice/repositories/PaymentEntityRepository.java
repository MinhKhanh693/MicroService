package mk.paymentservice.repositories;

import mk.paymentservice.controllers.responses.PaymentResponseDto;
import mk.paymentservice.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentEntityRepository extends JpaRepository<PaymentEntity, Integer>, JpaSpecificationExecutor<PaymentEntity> {
    @Query("SELECT new mk.paymentservice.controllers.responses.PaymentResponseDto(p.id, p.amount, p.createdDate, p.lastModifiedDate, p.orderId) FROM PaymentEntity p")
    List<PaymentResponseDto> findPaymentDTOs();
}