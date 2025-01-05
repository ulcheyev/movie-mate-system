package cz.cvut.moviemate.recommendationservice.client;


import cz.cvut.moviemate.commonlib.dto.GenreResponse;
import cz.cvut.moviemate.commonlib.dto.MovieDetailsDto;
import cz.cvut.moviemate.recommendationservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@FeignClient(
        name = "api-gateway",
        configuration = FeignConfig.class
)
public interface MovieServiceClient {
    @GetMapping(value = "/movie-mate/movies/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    MovieDetailsDto getMovie(@PathVariable String id);

    @PostMapping(value = "/movie-mate/movies/all-by-ids", produces = MediaType.APPLICATION_JSON_VALUE)
    List<MovieDetailsDto> getMovies(@RequestBody List<String> ids);

    @GetMapping(value = "/movie-mate/movies/genre/top", produces = MediaType.APPLICATION_JSON_VALUE)
    List<GenreResponse> getTopGenres();

    @PostMapping(value = "/movie-mate/movies/get-by-genres", produces = MediaType.APPLICATION_JSON_VALUE)
    Set<String> getMovieIdsByGenre(@RequestBody List<String> genres);
}
