package cz.cvut.moviemate.apigateway.client;

import cz.cvut.moviemate.apigateway.client.dto.AppUserDetails;
import cz.cvut.moviemate.apigateway.utils.Constants;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class WebAuthClientService {

    private final WebClient.Builder builder;
    private WebClient webClient;
    @Value("${MOVIE_MATE_USER_SERVICE_URI}")
    private String userServiceUri;

    @Value("${MOVIE_MATE_USER_SERVICE_VALIDATE_TOKEN_API_PATH}")
    private String validateTokenApiPath;

    @Autowired
    public WebAuthClientService(WebClient.Builder webClientBuilder) {
        this.builder = webClientBuilder;
    }

    @PostConstruct
    protected void init() {
        this.webClient = builder.baseUrl(userServiceUri).build();
    }

    public Mono<AppUserDetails> validateToken(String token) {
        return webClient
                .post()
                .uri(validateTokenApiPath)
                .header(Constants.AUTH_HEADER, Constants.BEARER_TOKEN.concat(token))
                .retrieve()
                .bodyToMono(AppUserDetails.class);
    }

}