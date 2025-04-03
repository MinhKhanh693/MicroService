package mk.notificationservice.services;

import jakarta.mail.MessagingException;
import mk.notificationservice.kafka.order.ProductDto;
import org.springframework.scheduling.annotation.Async;

import java.math.BigDecimal;
import java.util.List;

public interface IEmailService {
    @Async
    void sendPaymentSuccessEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference
    ) throws MessagingException;

    @Async
    void sendOrderConfirmationEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference,
            List<ProductDto> products
    ) throws MessagingException;
}
