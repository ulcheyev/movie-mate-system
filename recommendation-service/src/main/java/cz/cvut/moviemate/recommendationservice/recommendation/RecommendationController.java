package cz.cvut.moviemate.recommendationservice.recommendation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;


    @GetMapping("/{userId}")
    public ResponseEntity<Recommendations> getRecommendations(@PathVariable String userId) {
        return ResponseEntity.ok(recommendationService.getRecommendations(userId));
    }

}
