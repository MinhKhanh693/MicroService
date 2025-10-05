package mk.auth.service.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.auth.service.dtos.requests.SigningRequestDto;
import mk.auth.service.dtos.response.TblUserEntityResponseDto;
import mk.auth.service.dtos.response.TokenResponseDto;
import mk.auth.service.services.IAuthenticationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auths")
@RequiredArgsConstructor
@Slf4j(topic = "AUTH_CONTROLLER")
@CrossOrigin(origins = "*")
@Tag(name = "Auth Controller", description = "This is a simple auth controller")
public class AuthController {

    private final IAuthenticationService authenticationService;

    @Operation(summary = "Get access token", description = "This is a simple endpoint to get access token")
    @PostMapping("/access-token")
    public TokenResponseDto getAccessToken(@RequestBody SigningRequestDto signingRequestDto) {
        log.info("Get access token");
        return authenticationService.getAccessToken(signingRequestDto);
    }

    @Operation(summary = "Refresh token", description = "Get access token by refresh token")
    @PostMapping("/refresh-token")
    public TokenResponseDto refreshToken(@RequestParam String refreshToken) {
        log.info("Refresh token request");

        return authenticationService.refreshToken(refreshToken);
    }

    @Operation(summary = "Validate token", description = "Validate token")
    @GetMapping("/validate-token")
    public TblUserEntityResponseDto validateToken(@RequestParam String token, @RequestParam String uri) {
        log.info("Validate token request");
        return authenticationService.validateToken(token, uri);
    }
}
