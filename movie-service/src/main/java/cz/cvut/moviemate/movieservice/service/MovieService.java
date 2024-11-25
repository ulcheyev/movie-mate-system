package cz.cvut.moviemate.movieservice.service;

import cz.cvut.moviemate.movieservice.dto.GenreResponse;
import cz.cvut.moviemate.movieservice.dto.prop.GenreDto;

import java.util.List;

public interface MovieService {

    List<GenreResponse> bulkSaveGenres(List<GenreDto> genres);

    GenreResponse saveGenre(GenreDto genre);

    List<GenreResponse> getAllGenres();

    GenreResponse updateGenre(String id, GenreDto genre);

    void deleteGenre(String id);
}
