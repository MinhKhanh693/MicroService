package mk.orderservice.controllers.responses;

public record CustomerResponseDto(
        String id,
        String firstName,
        String lastName,
        String email
) {
}
