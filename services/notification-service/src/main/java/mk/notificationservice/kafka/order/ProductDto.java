package mk.notificationservice.kafka.order;

import java.math.BigDecimal;

public record ProductDto(Integer productId,
                         String name,
                         String description,
                         BigDecimal price,
                         double quantity) {
}
