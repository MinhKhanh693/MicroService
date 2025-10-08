package mk.paymentservice.mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import mk.paymentservice.controllers.requests.PaymentRequestDto;
import mk.paymentservice.controllers.responses.PaymentResponseDto;
import mk.paymentservice.entities.PaymentEntity;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-03T15:33:27+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public PaymentEntity toPayment(PaymentRequestDto request) {
        if ( request == null ) {
            return null;
        }

        PaymentEntity.PaymentEntityBuilder paymentEntity = PaymentEntity.builder();

        paymentEntity.id( request.id() );
        paymentEntity.amount( request.amount() );
        paymentEntity.paymentMethod( request.paymentMethod() );
        paymentEntity.orderId( request.orderId() );

        return paymentEntity.build();
    }

    @Override
    public PaymentResponseDto toDto(PaymentEntity payment) {
        if ( payment == null ) {
            return null;
        }

        Integer id = null;
        BigDecimal amount = null;
        LocalDateTime createdDate = null;
        LocalDateTime lastModifiedDate = null;
        Integer orderId = null;

        id = payment.getId();
        amount = payment.getAmount();
        createdDate = payment.getCreatedDate();
        lastModifiedDate = payment.getLastModifiedDate();
        orderId = payment.getOrderId();

        PaymentResponseDto paymentResponseDto = new PaymentResponseDto( id, amount, createdDate, lastModifiedDate, orderId );

        return paymentResponseDto;
    }
}
