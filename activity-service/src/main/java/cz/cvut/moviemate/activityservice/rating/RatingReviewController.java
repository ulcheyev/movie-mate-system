package cz.cvut.moviemate.activityservice.rating;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class RatingReviewController {

    private final RatingReviewService service;
    private final ReviewMapper reviewMapper;


    @GetMapping("/{movieId}")
    public List<RatingReview> getReviewsByMovie(@PathVariable String movieId) {
        return service.getReviewsByMovie(movieId);
    }

    @PostMapping
    public RatingReview submitReview(@RequestBody @Valid ReviewDto review) {
        RatingReview entity = reviewMapper.toEntity(review);
        entity.setKey(reviewMapper.extractRatingReviewKey(review));
        return service.saveReview(entity);
    }


    @PutMapping
    public RatingReview updateReview(@RequestBody @Valid ReviewDto review) {
        RatingReview entity = reviewMapper.toEntity(review);
        entity.setKey(reviewMapper.extractRatingReviewKey(review));
        return service.updateReview(entity);
    }


    @DeleteMapping
    public void deleteReview(@RequestParam String movieId,
                                     @RequestParam String username,
                                     @RequestParam Instant timestamp) {
       service.deleteReview(username, movieId, timestamp);
    }




}
