package cz.cvut.moviemate.recommendationservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.repository.cdi.Eager;

import java.util.List;

@Node
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;

    private String username;

    @Relationship(type = "INTERESTED", direction = Relationship.Direction.OUTGOING)
    private List<InterestedRelationship> interestedIn;

    @Relationship(type = "RATED", direction = Relationship.Direction.OUTGOING)
    private List<RatedRelationship> ratedMovies;

    @Relationship(type = "FRIENDS_WITH", direction = Relationship.Direction.OUTGOING)
    private List<User> friends;

    @Relationship(type = "FRIENDS_WITH", direction = Relationship.Direction.INCOMING)
    private List<User> friendOf;

    public User(String userId, String username) {
        this.username = username;
        this.id = userId;
    }
}
