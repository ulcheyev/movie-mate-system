package cz.cvut.moviemate.movieservice.repository;

import cz.cvut.moviemate.movieservice.model.Watchlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchlistRepository extends MongoRepository<Watchlist, String> {
}
