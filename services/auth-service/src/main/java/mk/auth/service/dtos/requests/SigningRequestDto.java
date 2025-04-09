package mk.auth.service.dtos.requests;

public record SigningRequestDto(
        String username,
        String password,
        String platform,
        String deviceToken,
        String version
) {
}
