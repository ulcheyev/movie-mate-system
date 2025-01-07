package cz.cvut.moviemate.recommendationservice.foaf;

import cz.cvut.moviemate.recommendationservice.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FOAFRepository extends Neo4jRepository<User, String> {

    @Query("""
        MATCH (u1:User {id: $userId1}), (u2:User {id: $userId2})
        MERGE (u1)-[:FRIENDS_WITH]->(u2)
        RETURN u1
    """)
    User createFriendship(String userId1, String userId2);


    @Query("""
        MATCH (u1:User {id: $userId1})-[r1:FRIENDS_WITH]-(u2:User {id: $userId2})
        DELETE r1
        RETURN u1
    """)
    User removeFriendship(String userId1, String userId2);


    @Query("""
    MATCH (u:User {id: $userId})
    OPTIONAL MATCH (u)-[r:RATED]->(m:Movie)
    RETURN u, collect(r) AS ratedRelations, collect(m) AS movies
    """)
    Optional<User> findUserById(@Param("userId")String userId);


    @Query("MATCH (u:User {id: $userId})-[:FRIENDS_WITH]-(f:User) RETURN f")
    List<User> findFriendsByUserId(String userId);

}
