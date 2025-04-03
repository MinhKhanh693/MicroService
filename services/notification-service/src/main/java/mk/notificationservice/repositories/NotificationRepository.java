package mk.notificationservice.repositories;

import mk.notificationservice.entities.NotificationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<NotificationEntity, String> {
}
