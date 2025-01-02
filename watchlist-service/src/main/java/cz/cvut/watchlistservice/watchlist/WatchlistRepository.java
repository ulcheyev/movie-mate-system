package cz.cvut.watchlistservice.watchlist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchlistRepository extends MongoRepository<Watchlist, String> {
    Page<Watchlist> findAllByUserId(String username, Pageable pageable);
}
