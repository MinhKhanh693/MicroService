package mk.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import mk.gateway.dto.response.TblUserEntityResponseDto;
import mk.gateway.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j(topic = "JWT_VALIDATION_FILTER")
public class JwtValidationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String USER_ID_HEADER = "X-User-Id";
    private static final String USER_NAME_HEADER = "X-User-Name";
    private static final String[] WHITELIST = {"/api/v1/auths/access-token", "/api/v1/auths/refresh-token"};

    private final WebClient webClient;

    public JwtValidationGatewayFilterFactory(WebClient.Builder webClientBuilder, @Value("${application.config.auth-service-url}") String authServiceUrl) {
        this.webClient = webClientBuilder.baseUrl(authServiceUrl).build();
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            for (String path : WHITELIST) {
                if (exchange.getRequest().getURI().getPath().equals(path)) {
                    log.info("Request is whitelisted, skipping token validation");
                    return chain.filter(exchange);
                }
            }

            String token = this.extractToken(exchange);
            if (token == null) {
                return this.unauthorizedResponse(exchange);
            }
            log.info("Token: {}", token);

            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/validate-token")
                            .queryParam("token", token)
                            .queryParam("uri", exchange.getRequest().getURI().toString())
                            .build())
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, response -> Mono.error(new InvalidTokenException("Invalid token")))
                    .bodyToMono(TblUserEntityResponseDto.class)
                    .flatMap(userResponseDto -> {
                        this.addUserHeaders(exchange, userResponseDto);
                        return chain.filter(exchange);
                    })
                    .onErrorResume(e -> this.unauthorizedResponse(exchange));
        };
    }

    private String extractToken(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION_HEADER);
        if (token != null && token.startsWith(BEARER_PREFIX)) {
            return token.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    private void addUserHeaders(ServerWebExchange exchange, TblUserEntityResponseDto userResponseDto) {
        log.info("Adding X-User-Id header: {}", userResponseDto.id());
        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                .header(USER_ID_HEADER, String.valueOf(userResponseDto.id()))
                .header(USER_NAME_HEADER, userResponseDto.username())
                .build();
        exchange.mutate().request(mutatedRequest).build();
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().add("Content-Type", "application/json");
        exchange.getResponse().getHeaders().add("WWW-Authenticate", "Bearer");
        byte[] bytes = "{\"error\": \"Unauthorized\"}".getBytes();
        exchange.getResponse().getHeaders().setContentLength(bytes.length);
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)));
    }
}