package cz.cvut.moviemate.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Order(-1)
public class CustomGlobalFilter implements GlobalFilter {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Extract request metadata
        String method = exchange.getRequest().getMethod().name();
        String uri = exchange.getRequest().getURI().toString();
        String headers = exchange.getRequest().getHeaders().toString();

        // Log request metadata
        log.info("Request received at {}: {} {} \nHeaders: \n{}",
                LocalDateTime.now().format(formatter), method, uri, headers);

        return chain.filter(exchange);
    }

}