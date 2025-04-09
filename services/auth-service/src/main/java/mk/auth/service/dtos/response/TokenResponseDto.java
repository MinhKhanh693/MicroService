package mk.auth.service.dtos.response;

import lombok.Builder;

@Builder
public record TokenResponseDto(
        String accessToken,
        String refreshToken,
        long expiresIn
) {
}
