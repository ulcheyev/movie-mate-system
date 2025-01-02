package cz.cvut.moviemate.recommendationservice.recommendation;

import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recommendations {
    private String userId;
    private Set<String> recommendedMovieIds;
}
