package cz.cvut.moviemate.apigateway.filter;

import cz.cvut.moviemate.apigateway.client.WebAuthClientService;
import cz.cvut.moviemate.apigateway.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(0)
@Slf4j
@RequiredArgsConstructor
public class AuthValidationFilter implements GatewayFilter {

    private final WebAuthClientService authServiceClient;
    private final RouteValidator routeValidator;

    private static String extractBearerToken(String authHeader) {
        return authHeader.substring(7);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (routeValidator.isSecured(request)) {
            log.warn("Request {} is secured", request.getURI());
            // Handle authentication and token validation
            String authHeader = request.getHeaders().getFirst(Constants.AUTH_HEADER);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.warn("Authorization header missing or invalid");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = extractBearerToken(authHeader);

            return authServiceClient.validateToken(token)
                    .flatMap(response -> {
                        exchange.getRequest()
                                .mutate()
                                .header(Constants.USERNAME_HEADER, response.username())
                                .header(Constants.EMAIL_HEADER, response.email())
                                .header(Constants.ROLES_HEADER, String.valueOf(response.roles()))
                                .header(Constants.AUTH_HEADER, Constants.BEARER_TOKEN.concat(token))
                                .build();
                        return chain.filter(exchange);
                    })
                    .onErrorResume(e -> {
                        log.error("Error during token validation", e);
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    });
        } else {
            log.warn("Request {} is not secured", request.getURI());
            // If route is unprotected, just pass the request along
            return chain.filter(exchange);
        }

    }

}
