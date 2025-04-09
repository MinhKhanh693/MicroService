package mk.paymentservice.services.impls;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.paymentservice.controllers.requests.PaymentRequestDto;
import mk.paymentservice.controllers.responses.PaymentResponseDto;
import mk.paymentservice.entities.PaymentEntity;
import mk.paymentservice.kafka.NotificationProducerKafkaService;
import mk.paymentservice.kafka.PaymentNotificationRequestDto;
import mk.paymentservice.mappers.PaymentMapper;
import mk.paymentservice.repositories.PaymentEntityRepository;
import mk.paymentservice.services.IPaymentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "PAYMENT_SERVICE")
public class PaymentServiceImpl implements IPaymentService {
    private final PaymentEntityRepository paymentEntityRepository;
    private final PaymentMapper paymentMapper;
    private final NotificationProducerKafkaService notificationProducerKafkaService;

    @Override
    public PaymentResponseDto createPayment(PaymentRequestDto request) {
        List<PaymentResponseDto> paymentDTOs = this.paymentEntityRepository.findPaymentDTOs();
        PaymentEntity newPayment = this.paymentMapper.toPayment(request);
        newPayment.setCreatedDate(LocalDateTime.now());
        var payment = this.paymentEntityRepository.save(newPayment);

        var PaymentNotificationRequest = PaymentNotificationRequestDto.builder()
                .orderReference(request.orderReference())
                .amount(request.amount())
                .paymentMethod(request.paymentMethod())
                .customerEmail(request.customer().email())
                .customerFirstName(request.customer().firstName())
                .customerLastName(request.customer().lastName())
                .build();

        this.notificationProducerKafkaService.sendNotification(
                PaymentNotificationRequest
        );
        log.info("Payment created with id = < {} >", payment.getId());
        return this.paymentMapper.toDto(payment);
    }
}
