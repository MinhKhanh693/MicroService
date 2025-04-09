package mk.auth.service.services.impls;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.auth.service.dtos.requests.SigningRequestDto;
import mk.auth.service.dtos.response.TblUserEntityResponseDto;
import mk.auth.service.dtos.response.TokenResponseDto;
import mk.auth.service.entities.TblPermissionEntity;
import mk.auth.service.entities.TblUserEntity;
import mk.auth.service.enums.TokenTypeEnum;
import mk.auth.service.exceptions.InvalidDataException;
import mk.auth.service.repositories.TblRoleEntityRepository;
import mk.auth.service.repositories.TblUserEntityRepository;
import mk.auth.service.services.IAuthenticationService;
import mk.auth.service.services.IJwtService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AUTHENTICATION_SERVICE")
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final TblUserEntityRepository userRepository;
    private final TblRoleEntityRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final IJwtService jwtService;

    @Override
    public TokenResponseDto getAccessToken(SigningRequestDto signingRequestDto) {
        log.info("Authenticating user: {}", signingRequestDto.username());
        authenticateUser(signingRequestDto.username(), signingRequestDto.password());

        TblUserEntity user = fetchUserByUsername(signingRequestDto.username());
        List<String> authorities = extractAuthorities(user);

        return generateTokenResponse(user, authorities);
    }

    @Override
    public TokenResponseDto refreshToken(String refreshToken) {
        if (!StringUtils.hasLength(refreshToken)) {
            throw new InvalidDataException("Refresh token is empty");
        }

        Claims payload = jwtService.extractPayload(refreshToken, TokenTypeEnum.REFRESH_TOKEN);
        TblUserEntity user = fetchUserByUsername(payload.getSubject());
        List<String> authorities = extractAuthorities(user);

        String accessToken = jwtService.generateAccessToken(user.getId(), user.getUsername(), authorities);
        return TokenResponseDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();
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
            throw new InternalAuthenticationServiceException("Authentication failed: " + e.getMessage());
        }
    }

    private TblUserEntity fetchUserByUsername(String username) {
        return userRepository.findTblUserByUsername(username);
    }

    private List<String> extractAuthorities(TblUserEntity user) {
        return user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

    private void validatePermissions(List<String> authorities, String uri) {

        List<TblPermissionEntity> permissions = roleRepository.findPermissionsByListRoleName(authorities);
        AntPathMatcher pathMatcher = new AntPathMatcher();

        boolean hasPermission = permissions.stream()
                .anyMatch(permission -> pathMatcher.match(permission.getPath(), uri));

        if (!hasPermission) {
            throw new AccessDeniedException("Access denied: insufficient permissions");
        }
    }

    private TokenResponseDto generateTokenResponse(TblUserEntity user, List<String> authorities) {
        String accessToken = jwtService.generateAccessToken(user.getId(), user.getUsername(), authorities);
        String refreshToken = jwtService.generateRefreshToken(user.getId(), user.getUsername(), authorities);
        return TokenResponseDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

    private TblUserEntityResponseDto buildUserResponse(TblUserEntity user) {
        return TblUserEntityResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .authorities(extractAuthorities(user))
                .build();
    }
}