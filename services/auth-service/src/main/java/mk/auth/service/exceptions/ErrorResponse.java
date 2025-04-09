package mk.auth.service.exceptions;

import lombok.Builder;

import java.util.Date;

@Builder
public record ErrorResponse(
        String message,
        String path,
        int status,
        String error,
        Date timestamp,
        String code
) {
}
