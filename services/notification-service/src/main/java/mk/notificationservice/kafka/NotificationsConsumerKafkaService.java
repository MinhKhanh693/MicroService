package mk.notificationservice.kafka;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.notificationservice.entities.NotificationEntity;
import mk.notificationservice.enums.NotificationTypeEnum;
import mk.notificationservice.kafka.order.OrderConfirmationDto;
import mk.notificationservice.kafka.payment.PaymentConfirmationDto;
import mk.notificationservice.repositories.NotificationRepository;
import mk.notificationservice.services.IEmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j(topic = "NOTIFICATION_CONSUMER_KAFKA_SERVICE")
@RequiredArgsConstructor
public class NotificationsConsumerKafkaService {
    private final NotificationRepository notificationRepository;

    private final IEmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotifications(PaymentConfirmationDto paymentConfirmation) throws MessagingException {
        log.info("Consuming the message from payment-topic Topic:: {}", paymentConfirmation);
        // Save notification
        var notification = NotificationEntity.builder()
                .type(NotificationTypeEnum.PAYMENT_CONFIRMATION)
                .notificationDate(LocalDateTime.now())
                .paymentConfirmation(paymentConfirmation)
                .build();

        this.notificationRepository.save(notification);
        log.info("Notification payment confirmation saved: {}", notification);

        var customerName = paymentConfirmation.customerFirstName() + " " + paymentConfirmation.customerLastName();
        this.emailService.sendPaymentSuccessEmail(
                paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotifications(OrderConfirmationDto orderConfirmation) throws MessagingException {
        log.info("Consuming the message from order-topic Topic:: {}", orderConfirmation);
        var notification = NotificationEntity.builder()
                .type(NotificationTypeEnum.ORDER_CONFIRMATION)
                .notificationDate(LocalDateTime.now())
                .orderConfirmation(orderConfirmation)
                .build();

        var notificationSaved = this.notificationRepository.save(notification);
        log.info("Notification order confirmation saved : {}", notificationSaved);

        var customerName = orderConfirmation.customer().firstName() + " " + orderConfirmation.customer().lastName();
        this.emailService.sendOrderConfirmationEmail(
                orderConfirmation.customer().email(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );
    }
}
