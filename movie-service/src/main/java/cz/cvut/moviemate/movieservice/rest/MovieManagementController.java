package cz.cvut.moviemate.movieservice.rest;

import cz.cvut.moviemate.movieservice.dto.GenreResponse;
import cz.cvut.moviemate.movieservice.dto.MessageResponse;
import cz.cvut.moviemate.movieservice.dto.MovieDetailsDto;
import cz.cvut.moviemate.movieservice.dto.MovieRequestDto;
import cz.cvut.moviemate.movieservice.dto.prop.GenreDto;
import cz.cvut.moviemate.movieservice.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manage")
@Slf4j(topic = "MOVIE_MANAGEMENT_CONTROLLER")
@RequiredArgsConstructor
public class MovieManagementController {

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

    @DeleteMapping(value = "/genre/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> deleteGenre(@PathVariable String id) {
        log.info("Deleting genre: {}", id);
        MessageResponse response = movieService.deleteGenre(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieDetailsDto> saveMovie(@RequestBody @Valid MovieRequestDto movieDto) {
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
