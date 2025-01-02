package cz.cvut.moviemate.commonlib.dto.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingActivityEvent {
    String movieId;
    String userId;
    int stars;
}
