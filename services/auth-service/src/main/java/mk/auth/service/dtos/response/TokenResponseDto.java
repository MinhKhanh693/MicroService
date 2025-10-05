package mk.auth.service.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.Instant;

@Builder
public record TokenResponseDto(
        String accessToken,
        String refreshToken,
        Instant expiration
) {
}
