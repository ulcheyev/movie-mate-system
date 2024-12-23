package cz.cvut.moviemate.recommendationservice.recommendation;

import cz.cvut.moviemate.recommendationservice.client.dto.MovieDto;

import java.util.List;

public interface RecommendationService {
    List<MovieDto> getRecommendations(String userId);
    void syncGraphData();
}
