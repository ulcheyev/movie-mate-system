package cz.cvut.watchlistservice.watchlist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchlistRepository extends MongoRepository<Watchlist, String> {
    Page<Watchlist> findAllByUsername(String username, Pageable pageable);
}
