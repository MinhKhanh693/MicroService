package mk.auth.service.services.impls;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import mk.auth.service.enums.TokenTypeEnum;
import mk.auth.service.services.IJwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j(topic = "JWT_SERVICE")
public class JwtServiceImpl implements IJwtService {

    @Value("${jwt.secretToken}")
    private String secretToken;
    @Value("${jwt.secretRefreshToken}")
    private String secretRefreshToken;
    @Value("${jwt.expiration}")
    private long jwtExpiration;
    @Value("${jwt.expirationRefreshToken}")
    private long refreshExpiration;

    @Override
    public String generateAccessToken(long userId, String username, List<String> authorities) {
        log.info("Generating access token for user: {} with authorities: {}", username, authorities);
        return generateToken(userId, username, authorities, jwtExpiration, TokenTypeEnum.ACCESS_TOKEN);
    }

    @Override
    public String generateRefreshToken(long userId, String username, List<String> authorities) {
        log.info("Generating refresh token for user: {} with authorities: {}", username, authorities);
        return generateToken(userId, username, authorities, refreshExpiration, TokenTypeEnum.REFRESH_TOKEN);
    }

    private String generateToken(long userId, String username, List<String> authorities, long expiration, TokenTypeEnum tokenType) {
        Map<String, Object> claims = Map.of(
                "userId", userId,
                "authorities", authorities
        );
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(getKey(tokenType), SignatureAlgorithm.HS256)
                .setHeader(Map.of("typ", "JWT", "alg", "HS256"))
                .compact();
    }

    @Override
    public Claims extractPayload(String token, TokenTypeEnum tokenType) {
        log.info("Extracting payload from token with type: {}", tokenType);
        return parseToken(token, tokenType);
    }

    private Claims parseToken(String token, TokenTypeEnum tokenType) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getKey(tokenType))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse token: " + e.getMessage(), e);
        }
    }

    private SecretKey getKey(TokenTypeEnum tokenType) {
        String secret = tokenType == TokenTypeEnum.ACCESS_TOKEN ? secretToken : secretRefreshToken;
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
}