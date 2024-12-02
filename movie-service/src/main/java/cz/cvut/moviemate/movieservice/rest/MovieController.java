package cz.cvut.moviemate.movieservice.rest;

import cz.cvut.moviemate.movieservice.dto.*;
import cz.cvut.moviemate.movieservice.dto.prop.GenreDto;
import cz.cvut.moviemate.movieservice.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<GenreResponse> saveGenre(@RequestBody @Valid GenreDto genreDto) {
        log.info("Saving genre: {}", genreDto);
        GenreResponse response = movieService.saveGenre(genreDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(value = "/genre/bulk", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GenreResponse>> saveAllGenres(@RequestBody @Valid List<GenreDto> genres) {
        log.info("Saving all genre: {}", genres);
        List<GenreResponse> savedGenres = movieService.bulkSaveGenres(genres);
        return new ResponseEntity<>(savedGenres, HttpStatus.CREATED);
    }

    @GetMapping(value = "/genre", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GenreResponse>> getAllGenres() {
        log.info("Getting all genres");
        List<GenreResponse> response = movieService.getAllGenres();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/genre/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> deleteGenre(@PathVariable String id) {
        log.info("Deleting genre: {}", id);
        MessageResponse response = movieService.deleteGenre(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieDetailsDto> saveMovie(@RequestBody @Valid  MovieRequestDto movieDto) {
        log.info("Saving movie: {}", movieDto);
        MovieDetailsDto response = movieService.saveMovie(movieDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(value = "/bulk", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MovieDetailsDto>> saveAllMovie(@RequestBody @Valid List<MovieRequestDto> movieDtos) {
        log.info("Saving movies");
        List<MovieDetailsDto> response = movieService.bulkSaveMovie(movieDtos);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieDetailsDto> getMovie(@PathVariable String id) {
        log.info("Getting movie: {}", id);
        MovieDetailsDto response = movieService.getMovie(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageDto<MoviePreviewDto>> getAllMovies(
            @RequestParam(value = "page", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "size", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "order", defaultValue = "ASC", required = false) Sort.Direction order
    ) {
        log.info("Getting all movies. Request parameters: pageNo: {}, pageSize: {}, order: {}, sortBy: {}", pageNo, pageSize, order, sortBy);
        PageDto<MoviePreviewDto> response = movieService.getAllMovies(pageNo, pageSize, order, sortBy);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> deleteMovie(@PathVariable String id) {
        log.info("Deleting movie: {}", id);
        MessageResponse response = movieService.deleteMovie(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieDetailsDto> updateMovie(@PathVariable String id, @RequestBody @Valid MovieRequestDto movieDto) {
        log.info("Updating movie: {}", movieDto);
        MovieDetailsDto response = movieService.updateMovie(id, movieDto);
        return ResponseEntity.ok(response);
    }
}
