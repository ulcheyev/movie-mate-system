package cz.cvut.moviemate.recommendationservice.model;

import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.time.LocalDate;

@RelationshipProperties
public class FriendsRelationship {

    @RelationshipId
    private Long id;

    @TargetNode
    private User friend;

    private LocalDate friendsSince;

}