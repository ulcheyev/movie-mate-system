package cz.cvut.moviemate.movieservice.config;

import cz.cvut.moviemate.movieservice.config.filter.SecurityFilter;
import cz.cvut.moviemate.movieservice.exception.handler.AccessDeniedExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityFilter securityFilter;
    private final AccessDeniedExceptionHandler accessDeniedExceptionHandler;

    @Bean
    static GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(getRequestsCustomizer())
                .exceptionHandling(getExceptionHandlingCustomizer())
                .build();
    }

    private Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>
    getRequestsCustomizer() {
        return auth -> auth
                .requestMatchers("/manage/**").hasAnyRole("ADMIN", "MODERATOR", "ROOT")
                .anyRequest()
                .authenticated();
    }

    private Customizer<ExceptionHandlingConfigurer<HttpSecurity>> getExceptionHandlingCustomizer() {
        return configurer -> configurer.accessDeniedHandler(accessDeniedExceptionHandler);
    }
}