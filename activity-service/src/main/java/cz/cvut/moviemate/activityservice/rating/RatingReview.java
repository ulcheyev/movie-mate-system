package cz.cvut.moviemate.activityservice.rating;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Map;
import java.util.Set;

@Table("ratings_reviews")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingReview {

    @PrimaryKey
    private RatingReviewKey key;
    private int rate;
    private String reviewText;
    private Set<String> tags;
    private Map<String, String> metadata;

}