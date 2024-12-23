package cz.cvut.moviemate.movieservice.rest;

import cz.cvut.moviemate.movieservice.dto.GenreResponse;
import cz.cvut.moviemate.movieservice.dto.MovieDetailsDto;
import cz.cvut.moviemate.movieservice.dto.MoviePreviewDto;
import cz.cvut.moviemate.movieservice.dto.PageDto;
import cz.cvut.moviemate.movieservice.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
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

    @GetMapping(value = "/genre", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GenreResponse>> getAllGenres() {
        log.info("Getting all genres");
        List<GenreResponse> response = movieService.getAllGenres();
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieDetailsDto> getMovie(@PathVariable String id) {
        log.info("Getting movie: {}", id);
        MovieDetailsDto response = movieService.getMovie(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/all-by-ids", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MovieDetailsDto>> getMovies(@RequestBody List<String> ids) {
        log.info("Getting movies: {}", ids);
        return ResponseEntity.ok(movieService.getMovies(ids));
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
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
}
