package mk.gateway.filter;

import mk.gateway.dto.response.TblUserEntityResponseDto;
import mk.gateway.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtValidationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final WebClient webClient;

    public JwtValidationGatewayFilterFactory(WebClient.Builder webClientBuilder, @Value("${application.config.auth-service-url}") String authServiceUrl) {
        this.webClient = webClientBuilder.baseUrl(authServiceUrl).build();
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            String token = this.extractToken(exchange);
            if (token == null) {
                return this.unauthorizedResponse(exchange);
            }

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
        exchange.getRequest().mutate()
                .header("X-User-Id", String.valueOf(userResponseDto.id()))
                .header("X-User-Name", userResponseDto.username())
                .build();
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