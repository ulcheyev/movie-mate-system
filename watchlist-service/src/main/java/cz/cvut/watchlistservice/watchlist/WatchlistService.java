package cz.cvut.watchlistservice.watchlist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WatchlistService {
    Watchlist createWatchlist(Watchlist watchlist);
    Watchlist getWatchlistById(String id);
    List<Watchlist> getWatchlistsByUsername(String userId, Pageable pageable);
    Watchlist updateWatchlist(String id, Watchlist watchlist);
    void deleteWatchlist(String id);
}