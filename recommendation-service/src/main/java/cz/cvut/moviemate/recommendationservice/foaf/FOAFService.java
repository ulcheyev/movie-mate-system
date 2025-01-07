package cz.cvut.moviemate.recommendationservice.foaf;

import cz.cvut.moviemate.commonlib.exception.InvalidArgumentException;
import cz.cvut.moviemate.commonlib.exception.NotFoundException;
import cz.cvut.moviemate.recommendationservice.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FOAFService {

    private final FOAFRepository foafRepository;

    public User createUserNode(String userId, String username) {
        Optional<User> existing = foafRepository.findById(userId);
        if (existing.isPresent()) {
            return existing.get();
        }

        User user = new User(userId, username);
        return foafRepository.save(user);
    }


    public User addFriend(String userId1, String userId2) {
        if (userId1.equals(userId2)) {
            throw new InvalidArgumentException("Cannot friend yourself!");
        }

        getUserNodeOrThrow(userId1);
        getUserNodeOrThrow(userId2);

        return foafRepository.createFriendship(userId1, userId2);
    }

    public User getUserNodeOrThrow(String userId) {
        return foafRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));
    }


    public UserDTO getUserNodeWithDirectFriends(String userId) {
        User user = foafRepository.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));

        List<User> friends = foafRepository.findFriendsByUserId(userId);

        List<FriendDTO> friendDtos = friends.stream()
                .map(friend -> new FriendDTO(friend.getId(), friend.getUsername()))
                .collect(Collectors.toList());

        List<String> rated = user.getRatedMovies().stream().map(rate -> rate.getMovie().getId()).toList();
        System.out.println(user.getRatedMovies().get(0).getMovie().getId());

        return new UserDTO(user.getId(), user.getUsername(), friendDtos, rated);

    }



    public User removeFriend(String userId1, String userId2) {
        if (userId1.equals(userId2)) {
            throw new InvalidArgumentException("Cannot unfriend yourself!");
        }
        getUserNodeOrThrow(userId1);
        getUserNodeOrThrow(userId2);

        return foafRepository.removeFriendship(userId1, userId2);
    }


}
