package cz.cvut.moviemate.apigateway.config;

import cz.cvut.moviemate.apigateway.filter.AuthValidationFilter;
import cz.cvut.moviemate.apigateway.filter.CustomGlobalFilter;
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
public class GatewayConfig {

    private final AuthValidationFilter authValidationFilter;

    @Value("${MOVIE_MATE_USER_SERVICE_URI}")
    private String userServiceUri;

    @Value("${MOVIE_MATE_WATCHLIST_SERVICE_URI}")
    private String watchlistServiceUri;

    @Value("${MOVIE_MATE_GATEWAY_USERS_ORIGINAL_BASE_PATH}")
    private String usersOriginalBasePath;

    @Value("${MOVIE_MATE_GATEWAY_USERS_TARGET_BASE_PATH}")
    private String usersTargetBasePath;

    @Value("${MOVIE_MATE_GATEWAY_WATCHLISTS_ORIGINAL_BASE_PATH}")
    private String watchlistsOriginalBasePath;

    @Value("${MOVIE_MATE_GATEWAY_WATCHLISTS_TARGET_BASE_PATH}")
    private String watchlistsTargetBasePath;

    public GatewayConfig(@Lazy AuthValidationFilter authValidationFilter) {
        this.authValidationFilter = authValidationFilter;
    }


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(r -> r
                        .path(usersOriginalBasePath + "/**")
                        .filters(f ->
                                rewriteBasePath(f, usersOriginalBasePath, usersTargetBasePath)
                                        .filter(authValidationFilter)
                        )
                        .uri(userServiceUri))
                .route(r -> r
                        .path(watchlistsOriginalBasePath + "/**")
                        .filters(f ->
                                rewriteBasePath(f, watchlistsOriginalBasePath, watchlistsTargetBasePath)
                                        .filter(authValidationFilter)
                        )
                        .uri(watchlistServiceUri))
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