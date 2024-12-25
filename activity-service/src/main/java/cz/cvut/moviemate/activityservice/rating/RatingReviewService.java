package cz.cvut.moviemate.activityservice.rating;

import java.time.Instant;
import java.util.List;

public interface RatingReviewService {

    List<RatingReview> getReviewsByMovie(String movieId);

    RatingReview saveReview(RatingReview review);

    RatingReview updateReview(RatingReview review);

    void deleteReview(String username, String movieId, Instant timestamp);

}
