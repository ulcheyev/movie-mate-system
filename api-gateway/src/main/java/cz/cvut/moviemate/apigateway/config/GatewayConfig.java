package cz.cvut.moviemate.apigateway.config;

import cz.cvut.moviemate.apigateway.filter.AuthValidationFilter;
import cz.cvut.moviemate.apigateway.filter.CustomGlobalFilter;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GatewayConfig {

    private final AuthValidationFilter authValidationFilter;
    private final GatewayRoutes gatewayRoutes;

    public GatewayConfig(@Lazy AuthValidationFilter authValidationFilter, GatewayRoutes gatewayRoutes) {
        this.authValidationFilter = authValidationFilter;
        this.gatewayRoutes = gatewayRoutes;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .path(gatewayRoutes.getUsersBasePath().getOriginal() + "/**")
                        .filters(f ->
                                rewriteBasePath(f, gatewayRoutes.getUsersBasePath().getOriginal(), gatewayRoutes.getUsersBasePath().getTarget())
                                        .filter(authValidationFilter)
                        )
                        .uri(gatewayRoutes.getUserServiceUri()))
                .route(r -> r
                        .path(gatewayRoutes.getWatchlistsBasePath().getOriginal() + "/**")
                        .filters(f ->
                                rewriteBasePath(f, gatewayRoutes.getWatchlistsBasePath().getOriginal(), gatewayRoutes.getWatchlistsBasePath().getTarget())
                                        .filter(authValidationFilter)
                        )
                        .uri(gatewayRoutes.getWatchlistServiceUri()))
                .route(r -> r
                        .path(gatewayRoutes.getMoviesBasePath().getOriginal() + "/**")
                        .filters(f ->
                                rewriteBasePath(f, gatewayRoutes.getMoviesBasePath().getOriginal(), gatewayRoutes.getMoviesBasePath().getTarget())
                                        .filter(authValidationFilter)
                        )
                        .uri(gatewayRoutes.getMovieServiceUri())
                )
                .build();
    }

    private GatewayFilterSpec rewriteBasePath(GatewayFilterSpec filters, String originalBasePath, String targetBasePath) {
        return filters.rewritePath(
                originalBasePath + "/(?<remaining>.*)",
                targetBasePath + "/${remaining}"
        );
    }

    @Bean
    public GlobalFilter customFilter() {
        return new CustomGlobalFilter();
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}