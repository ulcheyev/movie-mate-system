package cz.cvut.moviemate.recommendationservice.recommendation;

import cz.cvut.moviemate.commonlib.dto.events.UserSignUpEvent;

public interface RecommendationService {
    Recommendations getRecommendations(String userId);
    void syncGraphToNewUser(UserSignUpEvent event);
}
