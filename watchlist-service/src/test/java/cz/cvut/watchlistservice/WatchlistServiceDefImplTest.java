package cz.cvut.watchlistservice;

import cz.cvut.moviemate.commonlib.exception.NotFoundException;
import cz.cvut.watchlistservice.watchlist.Watchlist;
import cz.cvut.watchlistservice.watchlist.WatchlistRepository;
import cz.cvut.watchlistservice.watchlist.impl.WatchlistServiceDefImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class WatchlistServiceDefImplTest {

    @Mock
    private WatchlistRepository watchlistRepository;

    @InjectMocks
    private WatchlistServiceDefImpl watchlistService;

    @Test
    public void testCreateWatchlist() {

        Watchlist watchlist = Watchlist.builder()
                .username("user1")
                .name("Favorites")
                .moviesId(Arrays.asList("tt1375666", "tt0133093"))
                .build();

        Watchlist savedWatchlist = Watchlist.builder()
                .id("wl1")
                .username("user1")
                .name("Favorites")
                .moviesId(Arrays.asList("tt1375666", "tt0133093"))
                .dateCreated(LocalDate.now())
                .build();

        when(watchlistRepository.save(any(Watchlist.class))).thenReturn(savedWatchlist);

        Watchlist result = watchlistService.createWatchlist(watchlist);

        assertNotNull(result.getId());
        assertEquals("Favorites", result.getName());
        verify(watchlistRepository, times(1)).save(watchlist);
    }

    @Test
    public void testGetWatchlistById() {
        Watchlist watchlist = Watchlist.builder()
                .id("wl1")
                .username("user1")
                .name("Favorites")
                .moviesId(Arrays.asList("tt1375666", "tt0133093"))
                .dateCreated(LocalDate.now())
                .build();

        when(watchlistRepository.findById("wl1")).thenReturn(Optional.ofNullable(watchlist));

        Watchlist result = watchlistService.getWatchlistById("wl1");

        assertNotNull(result);
        assertEquals("Favorites", result.getName());
        verify(watchlistRepository, times(1)).findById("wl1");
    }

    @Test
    public void testUpdateWatchlist() {
        Watchlist existingWatchlist = Watchlist.builder()
                .id("wl1")
                .username("user1")
                .name("Favorites")
                .moviesId(Arrays.asList("tt1375666", "tt0133093"))
                .dateCreated(LocalDate.now())
                .build();

        Watchlist updatedWatchlist = Watchlist.builder()
                .id("wl1")
                .username("user1")
                .name("Updated Favorites")
                .moviesId(Arrays.asList("tt1375666", "tt0133093", "tt0468569"))
                .dateCreated(LocalDate.now())
                .build();

        when(watchlistRepository.findById("wl1")).thenReturn(Optional.of(existingWatchlist));
        when(watchlistRepository.save(existingWatchlist)).thenReturn(updatedWatchlist);

        Watchlist result = watchlistService.updateWatchlist("wl1", updatedWatchlist);

        assertEquals("Updated Favorites", result.getName());
        assertEquals(3, result.getMoviesId().size());
        verify(watchlistRepository, times(1)).findById("wl1");
        verify(watchlistRepository, times(1)).save(existingWatchlist);
    }

    @Test
    public void testDeleteWatchlist() {
        when(watchlistRepository.existsById("wl1")).thenReturn(true);
        doNothing().when(watchlistRepository).deleteById("wl1");

        watchlistService.deleteWatchlist("wl1");

        verify(watchlistRepository, times(1)).existsById("wl1");
        verify(watchlistRepository, times(1)).deleteById("wl1");
    }

    @Test
    public void testDeleteWatchlist_NotFound() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            watchlistService.deleteWatchlist("wl1");
        });

        assertEquals("Watchlist not found with id: wl1", exception.getMessage());
        verify(watchlistRepository, times(1)).existsById("wl1");
        verify(watchlistRepository, times(0)).deleteById(anyString());
    }
}
