package cz.cvut.moviemate.recommendationservice.foaf;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.cvut.moviemate.recommendationservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/foaf")
@RequiredArgsConstructor
public class FOAFController {

    private final FOAFService foafService;



    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String userId) {
        return ResponseEntity.ok(foafService.getUserNodeWithDirectFriends(userId));
    }

    @PostMapping("/{userId1}/{userId2}")
    public ResponseEntity<User> addFriend(@PathVariable String userId1, @PathVariable String userId2) {
        User updated = foafService.addFriend(userId1, userId2);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{userId1}/{userId2}")
    public ResponseEntity<User> removeFriend(@PathVariable String userId1, @PathVariable String userId2) {
        User updated = foafService.removeFriend(userId1, userId2);
        return ResponseEntity.ok(updated);
    }
}
