package mk.paymentservice.mappers;

import mk.paymentservice.controllers.requests.PaymentRequestDto;
import mk.paymentservice.controllers.responses.PaymentResponseDto;
import mk.paymentservice.entities.PaymentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentEntity toPayment(PaymentRequestDto request);

    PaymentResponseDto toDto(PaymentEntity payment);
}
