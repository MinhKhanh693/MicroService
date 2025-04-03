package mk.paymentservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "NOTIFICATION_PRODUCER_KAFKA_SERVICE")
public class NotificationProducerKafkaService {

    private final KafkaTemplate<String, PaymentNotificationRequestDto> kafkaTemplate;

    public void sendNotification(PaymentNotificationRequestDto request) {
        log.info("Sending notification with body = < {} >", request);
        Message<PaymentNotificationRequestDto> message = MessageBuilder
                .withPayload(request)
                .setHeader(TOPIC, "payment-topic")
                .build();

        kafkaTemplate.send(message);
    }
}
