package mk.notificationservice.entities;

import lombok.*;
import mk.notificationservice.enums.NotificationTypeEnum;
import mk.notificationservice.kafka.order.OrderConfirmationDto;
import mk.notificationservice.kafka.payment.PaymentConfirmationDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class NotificationEntity {
    @Id
    private String id;
    private NotificationTypeEnum type;
    private LocalDateTime notificationDate;
    private OrderConfirmationDto orderConfirmation;
    private PaymentConfirmationDto paymentConfirmation;
}
