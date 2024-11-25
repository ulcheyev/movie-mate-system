package cz.cvut.moviemate.movieservice.service.impl;

import cz.cvut.moviemate.movieservice.repository.WatchlistRepository;
import cz.cvut.moviemate.movieservice.service.WatchlistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "BASE_WATCHLIST_SERVICE")
@RequiredArgsConstructor
public class BaseWatchlistService implements WatchlistService {

    private final WatchlistRepository watchlistRepository;

}
