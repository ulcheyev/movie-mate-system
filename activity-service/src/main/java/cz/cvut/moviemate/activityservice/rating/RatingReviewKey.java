package cz.cvut.moviemate.activityservice.rating;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.time.Instant;

@PrimaryKeyClass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingReviewKey {

    @PrimaryKeyColumn(name = "movie_id", type = PrimaryKeyType.PARTITIONED)
    private String movieId;

    @PrimaryKeyColumn(name = "user_id", type = PrimaryKeyType.CLUSTERED)
    private String userId;

    @PrimaryKeyColumn(name = "timestamp", type = PrimaryKeyType.CLUSTERED)
    private Instant timestamp;

}
