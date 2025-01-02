package cz.cvut.moviemate.recommendationservice.client;

import cz.cvut.moviemate.commonlib.utils.Constants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

public class AuthTokenRequestInterceptor implements RequestInterceptor {

    @Value("${spring.application.api-token}")
    private String API_TOKEN;

    @Override
    public void apply(RequestTemplate template) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getCredentials() instanceof String token) {
            template.header(Constants.AUTH_HEADER, token);
        } else {
            template.header(Constants.AUTH_HEADER, "Bearer " + API_TOKEN);
        }
    }

}