package cz.cvut.moviemate.movieservice.config;

import cz.cvut.moviemate.commonlib.utils.SecurityFilterUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public SecurityFilterUtil securityFilterUtil() {
        return new SecurityFilterUtil();
    }
}
