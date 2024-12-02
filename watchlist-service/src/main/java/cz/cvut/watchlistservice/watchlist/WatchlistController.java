package cz.cvut.watchlistservice.watchlist;

import cz.cvut.moviemate.commonlib.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/manage")
@RequiredArgsConstructor
public class WatchlistController {

    private final WatchlistService watchlistService;
    private final WatchlistMapper watchlistMapper;


    @PostMapping
    public ResponseEntity<WatchlistDTO> createWatchlist(@Validated @RequestBody WatchlistDTO watchlistDTO) {
        Watchlist watchlist = watchlistMapper.toEntity(watchlistDTO);
        Watchlist createdWatchlist = watchlistService.createWatchlist(watchlist);
        WatchlistDTO responseDTO = watchlistMapper.toDTO(createdWatchlist);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<WatchlistDTO> getWatchlistById(@PathVariable String id) {
        Watchlist watchlist = watchlistService.getWatchlistById(id);
        WatchlistDTO responseDTO = watchlistMapper.toDTO(watchlist);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<WatchlistDTO>> getWatchlistsByUserId(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        List<Watchlist> watchlists = watchlistService
                .getWatchlistsByUsername(
                SecurityUtils.getPrincipalUsername(SecurityContextHolder.getContext().getAuthentication()), pageable);
        List<WatchlistDTO> responseDTOs = watchlists.stream()
                .map(watchlistMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }


    @PutMapping("/{id}")
    public ResponseEntity<WatchlistDTO> updateWatchlist(@PathVariable String id, @Validated @RequestBody WatchlistDTO watchlistDTO) {
        Watchlist watchlist = watchlistMapper.toEntity(watchlistDTO);
        Watchlist updatedWatchlist = watchlistService.updateWatchlist(id, watchlist);
        WatchlistDTO responseDTO = watchlistMapper.toDTO(updatedWatchlist);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWatchlist(@PathVariable String id) {
        watchlistService.deleteWatchlist(id);
        return ResponseEntity.noContent().build();
    }
}
