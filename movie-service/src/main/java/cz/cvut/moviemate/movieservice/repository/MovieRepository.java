package cz.cvut.moviemate.movieservice.repository;

import cz.cvut.moviemate.movieservice.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
}
