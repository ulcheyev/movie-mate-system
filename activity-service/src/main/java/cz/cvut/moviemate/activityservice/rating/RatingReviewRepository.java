package cz.cvut.moviemate.activityservice.rating;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingReviewRepository extends CassandraRepository<RatingReview, RatingReviewKey> {

    List<RatingReview> findByKeyMovieId(String movieId);
}
