package mk.notificationservice.kafka.order;

public record CustomerDto(String id,
                          String firstName,
                          String lastName,
                          String email) {
}
