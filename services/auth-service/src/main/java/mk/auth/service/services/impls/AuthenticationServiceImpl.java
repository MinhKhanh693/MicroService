package mk.auth.service.services.impls;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.auth.service.dtos.TblPermissionEntityDTO;
import mk.auth.service.dtos.requests.SigningRequestDto;
import mk.auth.service.dtos.response.TblUserEntityResponseDto;
import mk.auth.service.dtos.response.TokenResponseDto;
import mk.auth.service.entities.TblUserEntity;
import mk.auth.service.enums.TokenTypeEnum;
import mk.auth.service.exceptions.InvalidDataException;
import mk.auth.service.repositories.TblUserEntityRepository;
import mk.auth.service.services.IAuthenticationService;
import mk.auth.service.services.IJwtService;
import mk.auth.service.services.IPermissionService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AUTHENTICATION_SERVICE")
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final TblUserEntityRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final IJwtService jwtService;
    private final IPermissionService permissionService;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public TokenResponseDto getAccessToken(SigningRequestDto signingRequestDto) {
        log.info("Authenticating user: {}", signingRequestDto.username());
        authenticateUser(signingRequestDto.username(), signingRequestDto.password());
        TblUserEntity user = fetchUserByUsername(signingRequestDto.username());
        List<String> authorities = extractAuthorities(user);
        // Use the extracted method for token generation and response building
        return generateAndBuildTokens(user, authorities);
    }

    @Override
    public TokenResponseDto refreshToken(String refreshToken) {
        if (!StringUtils.hasLength(refreshToken)) {
            throw new InvalidDataException("Refresh token is empty");
        }
        Claims payload = jwtService.extractPayload(refreshToken, TokenTypeEnum.REFRESH_TOKEN);
        TblUserEntity user = fetchUserByUsername(payload.getSubject());
        List<String> authorities = extractAuthorities(user);
        // Generate only a new access token
        String accessToken = jwtService.generateAccessToken(user.getId(), user.getUsername(), authorities);
        Instant expirationDate = jwtService.getExpirationDateFromToken(accessToken);
        // Return new access token with the original refresh token
        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken) // Keep the original refresh token
                .expiration(expirationDate)
                .build();
    }

    @Override
    public TblUserEntityResponseDto validateToken(String token, String uri) {
        log.info("Validating token for URI: {}", uri);
        String trimmedUri = uri.substring(uri.indexOf("/api/"));
        Claims payload = jwtService.extractPayload(token, TokenTypeEnum.ACCESS_TOKEN);
        List<String> authorities = payload.get("authorities", List.class);
        validatePermissions(authorities, trimmedUri);
        TblUserEntity user = fetchUserByUsername(payload.getSubject());
        return buildUserResponse(user);
    }

    private void authenticateUser(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException | DisabledException e) {
            // Log the error for debugging purposes
            log.error("Authentication failed for user {}: {}", username, e.getMessage());
            throw new InternalAuthenticationServiceException("Authentication failed: " + e.getMessage(), e);
        }
    }

    private TblUserEntity fetchUserByUsername(String username) {
        TblUserEntity user = userRepository.findTblUserByUsername(username);
        if (user == null) {
            // Throw a more specific exception if user not found
            throw new InvalidDataException("User not found: " + username);
        }
        return user;
    }

    private List<String> extractAuthorities(TblUserEntity user) {
        return user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

    /**
     * Validates if the user associated with the provided authorities has permission to access the given URI.
     *
     * @param authorities List of authority strings.
     * @param uri         The URI to check permissions for.
     * @throws AccessDeniedException if permission is denied.
     */
    public void validatePermissions(List<String> authorities, String uri) {
        List<TblPermissionEntityDTO> permissions = this.permissionService.getPermissionsByRoleNames(authorities);
        boolean hasPermission = permissions.stream()
                .anyMatch(permission -> permission != null && permission.getPath() != null &&
                        pathMatcher.match(permission.getPath(), uri));

        if (!hasPermission) {
            log.warn("ACCESS DENIED - URI: '{}', Authorities: {}. No matching permission found.", uri, authorities);
            throw new AccessDeniedException("Access is denied: insufficient permissions for " + uri);
        }
        log.debug("Permission granted for URI '{}' with authorities {}.", uri, authorities);
    }

    /**
     * Generates access and refresh tokens for the given user and authorities,
     * then builds the TokenResponseDto.
     *
     * @param user        The user entity.
     * @param authorities The user's authorities.
     * @return TokenResponseDto containing the generated tokens and expiration.
     */
    private TokenResponseDto generateAndBuildTokens(TblUserEntity user, List<String> authorities) {
        String accessToken = jwtService.generateAccessToken(user.getId(), user.getUsername(), authorities);
        String refreshToken = jwtService.generateRefreshToken(user.getId(), user.getUsername(), authorities);
        Instant expirationDate = jwtService.getExpirationDateFromToken(accessToken);
        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiration(expirationDate)
                .build();
    }

    private TblUserEntityResponseDto buildUserResponse(TblUserEntity user) {
        // Ensure authorities are loaded/extracted if needed, handled by extractAuthorities
        return TblUserEntityResponseDto.builder()
                .id(user.getId())
                // Add other relevant fields from TblUserEntity to the response DTO as needed
                .username(user.getUsername())
                .email(user.getEmail()) // Assuming email exists on TblUserEntity
                .authorities(extractAuthorities(user))
                .build();
    }
}