package mk.customerservice.exceptions;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {

}