package cz.cvut.moviemate.apigateway.filter;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RouteValidator {

    @Value("${MOVIE_MATE_USER_SERVICE_AUTH_API_PATH}")
    private String authApiPath;
    private List<String> unprotectedURLs;

    @PostConstruct
    protected void  init() {
        unprotectedURLs = List.of(authApiPath);
    }

    public boolean isSecured(ServerHttpRequest request) {
        return unprotectedURLs
                .stream()
                .noneMatch(uri -> request.getURI().getPath().contains(uri));
    }
}