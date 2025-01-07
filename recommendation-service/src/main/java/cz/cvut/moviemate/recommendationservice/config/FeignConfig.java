package cz.cvut.moviemate.recommendationservice.config;

import cz.cvut.moviemate.recommendationservice.client.AuthTokenRequestInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor authTokenRequestInterceptor() {
        return new AuthTokenRequestInterceptor();
    }

}
