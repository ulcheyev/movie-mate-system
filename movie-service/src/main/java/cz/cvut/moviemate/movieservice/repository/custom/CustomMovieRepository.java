package cz.cvut.moviemate.movieservice.repository.custom;

import java.util.List;

public interface CustomMovieRepository {

    void removeGenreFromMovieGenres(String genreId);

    List<String> getTopGenres();
}
