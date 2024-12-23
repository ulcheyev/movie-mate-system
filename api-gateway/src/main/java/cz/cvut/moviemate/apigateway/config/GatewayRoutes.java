package cz.cvut.moviemate.apigateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "gateway.routes")
@Getter
@Setter
public class GatewayRoutes {

    private String userServiceUri;
    private String watchlistServiceUri;
    private String movieServiceUri;
    private String activityServiceUri;

    private BasePath usersBasePath;
    private BasePath watchlistsBasePath;
    private BasePath moviesBasePath;
    private BasePath activityBasePath;


    @Getter
    @Setter
    public static class BasePath {
        private String original;
        private String target;
    }
}
