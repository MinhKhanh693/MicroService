package mk.auth.service.services;

import io.jsonwebtoken.Claims;
import mk.auth.service.enums.TokenTypeEnum;

import java.util.List;

public interface IJwtService {
    String generateAccessToken(long userId, String username, List<String> authorities);

    String generateRefreshToken(long userId, String username, List<String> authorities);

    Claims extractPayload(String token, TokenTypeEnum tokenType);
}
