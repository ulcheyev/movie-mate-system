package cz.cvut.moviemate.recommendationservice.foaf;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FriendDTO {
    private String id;
    private String username;
}
