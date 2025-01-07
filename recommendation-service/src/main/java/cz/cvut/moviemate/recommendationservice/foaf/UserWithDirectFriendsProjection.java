package cz.cvut.moviemate.recommendationservice.foaf;

import java.util.List;

public interface UserWithDirectFriendsProjection {
    String getId();
    String getUsername();
    List<FriendDTO> getFriends();
}
