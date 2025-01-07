package cz.cvut.moviemate.recommendationservice.foaf;

import lombok.Data;

@Data
public class UserNodeCreationRequest {
    private String userId;
    private String username;
}
