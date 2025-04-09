package mk.auth.service.controllers;

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
public class AuthController {

    private final IAuthenticationService authenticationService;

    @PostMapping("/access-token")
    public TokenResponseDto getAccessToken(@RequestBody SigningRequestDto signingRequestDto) {
        log.info("Get access token");
        return authenticationService.getAccessToken(signingRequestDto);
    }

    @PostMapping("/refresh-token")
    public TokenResponseDto refreshToken(@RequestBody String refreshToken) {
        log.info("Refresh token request");

        return authenticationService.refreshToken(refreshToken);
    }

    @GetMapping("/validate-token")
    public TblUserEntityResponseDto validateToken(@RequestParam String token, @RequestParam String uri) {
        log.info("Validate token request");
        return authenticationService.validateToken(token, uri);
    }
}
