package cz.cvut.watchlistservice.watchlist.impl;

import cz.cvut.moviemate.commonlib.dto.events.WatchlistEditEvent;
import cz.cvut.moviemate.commonlib.exception.NotFoundException;
import cz.cvut.moviemate.commonlib.utils.SecurityUtils;
import cz.cvut.watchlistservice.service.KafkaEventProducer;
import cz.cvut.watchlistservice.service.RedisCacheService;
import cz.cvut.watchlistservice.watchlist.Watchlist;
import cz.cvut.watchlistservice.watchlist.WatchlistMapper;
import cz.cvut.watchlistservice.watchlist.WatchlistRepository;
import cz.cvut.watchlistservice.watchlist.WatchlistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class WatchlistServiceDefImpl implements WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final WatchlistMapper watchlistMapper;
    private final RedisCacheService redisCacheService;
    private final KafkaEventProducer kafkaEventProducer;


    @Override
    public Watchlist createWatchlist(Watchlist watchlist) {
        log.info("Create watchlist: {}", watchlist);
        watchlist.setDateCreated(LocalDate.now());
        if (watchlist.getName() == null || watchlist.getName().isEmpty()) {
            watchlist.setName("My new watchlist");
        }
        watchlist.setUserId(SecurityUtils.getPrincipalID(SecurityContextHolder.getContext().getAuthentication()));
        redisCacheService.deleteKeysByPattern("watchlists::" + watchlist.getUserId() + "*");
        return watchlistRepository.save(watchlist);
    }

    @Override
    @Cacheable(value = "watchlist", key = "#id", unless = "#result == null")
    public Watchlist getWatchlistById(String id) {
        log.info("Get watchlist with ID: {}. Cache miss.", id);
        return watchlistRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Watchlist not found with id: " + id));
    }

    @Override
    @Cacheable(value = "watchlists", key = "#userId + '_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    public List<Watchlist> getWatchlistsByUserId(String userId, Pageable pageable) {
        log.info("Get watchlists for user ID: {}. Cache miss.", userId);
        Page<Watchlist> watchlists = watchlistRepository.findAllByUserId(userId, pageable);
        return watchlists.getContent();
    }

    @Override
    @Caching(
            put = {
                    @CachePut(value = "watchlist", key = "#id")
            }
    )
    public Watchlist updateWatchlist(String id, Watchlist watchlist) {
        log.info("Update watchlist with ID: {}.", id);
        Watchlist res = watchlistRepository.findById(id).map(existingWatchlist -> {
                    watchlistMapper.update(existingWatchlist, watchlist);
                    Watchlist updatedWatchlist = watchlistRepository.save(existingWatchlist);
                    redisCacheService.deleteKeysByPattern("watchlists::" + SecurityUtils.getPrincipalID(
                            SecurityContextHolder.getContext().getAuthentication()) + "*");
                    return updatedWatchlist;
                }
        ).orElseThrow(() -> new NotFoundException("Watchlist not found with id: " + id));
        kafkaEventProducer.publishWatchlistEvent(new WatchlistEditEvent(watchlist.getMoviesId(), res.getUserId()));
        return res;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "watchlist", key = "#id"),
    })
    public void deleteWatchlist(String id) {
        log.info("Delete watchlist with ID: {}.", id);
        if (watchlistRepository.existsById(id)) {
            watchlistRepository.deleteById(id);
            redisCacheService.deleteKeysByPattern("watchlists::" + SecurityUtils.getPrincipalID(
                    SecurityContextHolder.getContext().getAuthentication()) + "*");
        } else {
            throw new NotFoundException("Watchlist not found with id: " + id);
        }
    }
}
