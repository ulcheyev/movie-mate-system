package cz.cvut.moviemate.activityservice.rating.impl;

import cz.cvut.moviemate.activityservice.rating.*;
import cz.cvut.moviemate.activityservice.service.KafkaEventProducer;
import cz.cvut.moviemate.commonlib.dto.events.RatingActivityEvent;
import cz.cvut.moviemate.commonlib.exception.InvalidArgumentException;
import cz.cvut.moviemate.commonlib.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j(topic = "RATING_REVIEW_SERVICE")
@RequiredArgsConstructor
public class RatingReviewDefServiceImpl implements RatingReviewService {

    private final RatingReviewRepository ratingReviewRepository;
    private final ReviewMapper reviewMapper;
    private final KafkaEventProducer kafkaEventProducer;

    @Override
    @Cacheable(value = "ratings", key = "#movieId", unless = "#result == null")
    public List<RatingReview> getReviewsByMovie(String movieId) {
        log.info("Fetching reviews for movie with ID: {}", movieId);
        return ratingReviewRepository.findByKeyMovieId(movieId);
    }

    @Override
    @CacheEvict(value = "ratings", key = "#review.key.movieId")
    public RatingReview saveReview(RatingReview review) {
        log.info("Saving new review for movie with ID: {} by user: {}", review.getKey().getMovieId(), review.getKey().getUserId());
        if (review.getRate() < 1 || review.getRate() > 5) {
            log.error("Invalid rating: {}. Must be between 1 and 5.", review.getRate());
            throw new InvalidArgumentException("Rating must be between 1 and 5.");
        }
        kafkaEventProducer.publishRatingEvent(new RatingActivityEvent(review.getKey().getMovieId(),
                review.getKey().getUserId(), review.getRate()));
        return ratingReviewRepository.save(review);
    }

    @Override
    @CachePut(value = "ratings", key = "#review.key.movieId")
    public RatingReview updateReview(RatingReview review) {
        log.info("Updating review for movie with ID: {} by user: {}", review.getKey().getMovieId(), review.getKey().getUserId());
        RatingReviewKey key = review.getKey();
        RatingReview ratingReview = ratingReviewRepository.findById(key)
                .orElseThrow(() -> new NotFoundException("Review not found for the specified movie and user.") );
        reviewMapper.update(ratingReview, review);
        return ratingReviewRepository.save(review);
    }

    @Override
    @CacheEvict(value = "ratings", key = "#movieId")
    public void deleteReview(String username, String movieId, Instant timestamp) {
        log.info("Deleting review for movie with ID: {} by user: {}", movieId, username);

        if (username == null || movieId == null || timestamp == null) {
            log.error("Username, movieId or timestamp is null");
            throw new InvalidArgumentException("Username, movieId and timestamp cannot be null.");
        }

        RatingReviewKey key = new RatingReviewKey(movieId, username, timestamp);

        if (!ratingReviewRepository.existsById(key)) {
            log.error("Review not found for movie: {} and user: {}", movieId, username);
            throw new NotFoundException("Review not found for the specified movie and user.");
        }

        ratingReviewRepository.deleteById(key);
    }
}
