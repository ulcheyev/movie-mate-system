package cz.cvut.moviemate.movieservice.repository;

import cz.cvut.moviemate.movieservice.model.Genre;
import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends MongoRepository<Genre, String> {

    @ExistsQuery("{ 'name': ?0 }")
    boolean existsByName(String name);
}
