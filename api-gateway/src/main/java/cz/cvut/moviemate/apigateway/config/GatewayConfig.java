package cz.cvut.moviemate.apigateway.config;

import cz.cvut.moviemate.apigateway.filter.AuthValidationFilter;
import cz.cvut.moviemate.apigateway.filter.CustomGlobalFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
@Slf4j
public class GatewayConfig {

    private final AuthValidationFilter authValidationFilter;
    private final GatewayRoutes gatewayRoutes;


    @Value("${server.servlet.context-path}")
    private String BASE_CONTEXT_PATH;

    public GatewayConfig(@Lazy AuthValidationFilter authValidationFilter, GatewayRoutes gatewayRoutes) {
        this.authValidationFilter = authValidationFilter;
        this.gatewayRoutes = gatewayRoutes;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route("user-service-route", r -> r
                        .path(buildFullPath(gatewayRoutes.getUsersBasePath().getOriginal(),
                                gatewayRoutes.getUserServiceUri()))
                        .filters(f -> configureFilters(f,
                                gatewayRoutes.getUsersBasePath().getOriginal(),
                                gatewayRoutes.getUsersBasePath().getTarget()))
                        .uri(gatewayRoutes.getUserServiceUri()))
                .route("watchlist-service-route", r -> r
                        .path(buildFullPath(gatewayRoutes.getWatchlistsBasePath().getOriginal(),
                                gatewayRoutes.getWatchlistServiceUri()))
                        .filters(f -> configureFilters(f,
                                gatewayRoutes.getWatchlistsBasePath().getOriginal(),
                                gatewayRoutes.getWatchlistsBasePath().getTarget()))
                        .uri(gatewayRoutes.getWatchlistServiceUri()))
                .route("movie-service-route", r -> r
                        .path(buildFullPath(gatewayRoutes.getMoviesBasePath().getOriginal(),
                                gatewayRoutes.getMovieServiceUri()))
                        .filters(f -> configureFilters(f,
                                gatewayRoutes.getMoviesBasePath().getOriginal(),
                                gatewayRoutes.getMoviesBasePath().getTarget()))
                        .uri(gatewayRoutes.getMovieServiceUri()))
                .route("activity-service-route", r -> r
                        .path(buildFullPath(gatewayRoutes.getActivityBasePath().getOriginal(),
                                gatewayRoutes.getActivityServiceUri()))
                        .filters(f -> configureFilters(f,
                                gatewayRoutes.getActivityBasePath().getOriginal(),
                                gatewayRoutes.getActivityBasePath().getTarget()))
                        .uri(gatewayRoutes.getActivityServiceUri()))
                .route("recommendations-service-route", r -> r
                        .path(buildFullPath(gatewayRoutes.getRecommendationsBasePath().getOriginal(),
                                gatewayRoutes.getRecommendationsServiceUri()))
                        .filters(f -> configureFilters(f,
                                gatewayRoutes.getRecommendationsBasePath().getOriginal(),
                                gatewayRoutes.getRecommendationsBasePath().getTarget()))
                        .uri(gatewayRoutes.getRecommendationsServiceUri()))
                .route("api-docs-service-route", r -> r
                        .path(buildFullPath(gatewayRoutes.getApiDocsBasePath().getOriginal(),
                                gatewayRoutes.getApiDocsServiceUri()))
                        .filters(f -> configureFilters(f,
                                gatewayRoutes.getApiDocsBasePath().getOriginal(),
                                gatewayRoutes.getApiDocsBasePath().getTarget()))
                        .uri(gatewayRoutes.getApiDocsServiceUri()))
                .route("discovery-server-route", r -> r
                        .path(buildFullPath(gatewayRoutes.getDiscoveryServerBasePath().getOriginal(),
                                gatewayRoutes.getDiscoveryServerUiUri()))
                        .filters(f -> configureFilters(f,
                                gatewayRoutes.getDiscoveryServerBasePath().getOriginal(),
                                gatewayRoutes.getDiscoveryServerBasePath().getTarget()))
                        .uri(gatewayRoutes.getDiscoveryServerUiUri()))
                .build();
    }

    private String buildFullPath(String basePath, String uri) {
        String s = BASE_CONTEXT_PATH + basePath + "/**";
        log.warn("Builded path = " +uri+ s );
        return s;
    }

    private GatewayFilterSpec configureFilters(GatewayFilterSpec filters, String originalBasePath, String targetBasePath) {
        log.warn("Routing from " + originalBasePath + " to " + targetBasePath);
        return filters
                .rewritePath(BASE_CONTEXT_PATH + originalBasePath + "/(?<remaining>.*)", targetBasePath + "/${remaining}")
                .filter(authValidationFilter);
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
