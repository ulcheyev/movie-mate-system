package cz.cvut.moviemate.recommendationservice.recommendation;

import cz.cvut.moviemate.recommendationservice.model.Movie;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends Neo4jRepository<Movie, String> {

    @Query("""
        MATCH (u:User {id: $userId})-[:FRIENDS_WITH]-(friend:User)-[:RATED|INTERESTED]->(m:Movie)
        WHERE NOT (u)-[:RATED|INTERESTED]->(m)
        RETURN m, COUNT(friend) AS frequency
        ORDER BY frequency DESC
        LIMIT 2
    """)
    List<Movie> findMoviesThatUserFriendsRatedOrInterested(String userId);


    @Query("""
        MATCH (u:User {id: $userId})-[:RATED|INTERESTED]->(m:Movie)<-[:RATED|INTERESTED]-(other:User)
        WHERE other.id <> $userId AND NOT (u)-[:FRIENDS_WITH]-(other)
        MATCH (other)-[:RATED|INTERESTED]->(similar:Movie)
        WHERE NOT (u)-[:RATED|INTERESTED]->(similar)
        RETURN similar, COUNT(other) AS frequency
        ORDER BY frequency DESC
        LIMIT 2
    """)
    List<Movie> findMoviesThatOtherUsersRatedOrInterested(String userId);
}
