package cz.cvut.moviemate.movieservice.service;

import cz.cvut.moviemate.movieservice.dto.*;
import cz.cvut.moviemate.movieservice.dto.prop.GenreDto;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface MovieService {

    List<GenreResponse> bulkSaveGenres(List<GenreDto> genres);

    GenreResponse saveGenre(GenreDto genre);

    List<GenreResponse> getAllGenres();

    MessageResponse deleteGenre(String id);

    MovieDetailsDto saveMovie(MovieRequestDto movieDto);

    List<MovieDetailsDto> bulkSaveMovie(List<MovieRequestDto> movies);

    MovieDetailsDto getMovie(String id);

    List<MovieDetailsDto> getMovies(List<String> ids);

    MessageResponse deleteMovie(String id);

    MovieDetailsDto updateMovie(String id, MovieRequestDto movieDto);

    PageDto<MoviePreviewDto> getAllMovies(int pageNo, int pageSize, Sort.Direction order, String sortBy);
}
