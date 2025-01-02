package cz.cvut.moviemate.recommendationservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

@RelationshipProperties
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatedRelationship {

    @RelationshipId
    private Long id;

    @TargetNode
    private Movie movie;

    private int stars;
}
