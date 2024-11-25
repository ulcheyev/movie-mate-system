package cz.cvut.moviemate.movieservice.rest;

import cz.cvut.moviemate.movieservice.dto.GenreResponse;
import cz.cvut.moviemate.movieservice.dto.prop.GenreDto;
import cz.cvut.moviemate.movieservice.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@Slf4j(topic = "MOVIE_CONTROLLER")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping(value = "/genre", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenreResponse> saveGenre(@RequestBody GenreDto genreDto) {
        log.info("Saving genre: {}", genreDto);
        GenreResponse response = movieService.saveGenre(genreDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/genre", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GenreResponse>> getAllGenres() {
        log.info("Getting all genres");
        List<GenreResponse> response = movieService.getAllGenres();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/genre/{id}")
    public ResponseEntity<String> deleteGenre(@PathVariable String id) {
        log.info("Deleting genre: {}", id);
        movieService.deleteGenre(id);
        return ResponseEntity.ok(String.format("Deleted genre: %s", id));
    }
}
