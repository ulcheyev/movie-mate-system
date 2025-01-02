package cz.cvut.moviemate.recommendationservice.foaf;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String username;
    private List<FriendDTO> friends;
    private List<String> rated;
}
