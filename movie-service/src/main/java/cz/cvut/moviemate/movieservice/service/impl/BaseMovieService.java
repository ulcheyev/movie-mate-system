package cz.cvut.moviemate.movieservice.service.impl;

import cz.cvut.moviemate.commonlib.exception.DuplicateException;
import cz.cvut.moviemate.commonlib.exception.NotFoundException;
import cz.cvut.moviemate.movieservice.dto.GenreResponse;
import cz.cvut.moviemate.movieservice.dto.MessageResponse;
import cz.cvut.moviemate.movieservice.dto.mapper.GenreMapper;
import cz.cvut.moviemate.movieservice.dto.prop.GenreDto;
import cz.cvut.moviemate.movieservice.model.Genre;
import cz.cvut.moviemate.movieservice.repository.GenreRepository;
import cz.cvut.moviemate.movieservice.repository.MovieRepository;
import cz.cvut.moviemate.movieservice.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j(topic = "BASE_MOVIE_SERVICE")
@RequiredArgsConstructor
public class BaseMovieService implements MovieService {

    private final GenreMapper genreMapper;
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;

    private Genre getGenre(String id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Genre with id '%s' not found", id)));
    }

    private boolean isGenreExists(String name) {
        return genreRepository.existsByName(name);
    }

    @Override
    public List<GenreResponse> bulkSaveGenres(List<GenreDto> genres) {
        if (genres == null || genres.isEmpty())
            throw new IllegalArgumentException("The genre list cannot be null or empty.");

        List<Genre> genresToSave = genres.stream()
                .filter(genreDto -> {
                    if (!isGenreExists(genreDto.name()))
                        return true;

                    log.warn("Genre with name '{}' already exists. Skipping...", genreDto.name());
                    return true;
                })
                .map(genreDto -> Genre.builder().name(genreDto.name()).build())
                .toList();

        List<Genre> savedGenres = genreRepository.saveAll(genresToSave);
        return savedGenres.stream()
                .map(genreMapper::toDto)
                .toList();
    }

    @Override
    public GenreResponse saveGenre(GenreDto genre) {
        String genreName = genre.name();
        if (isGenreExists(genreName))
            throw new DuplicateException(String.format("Genre '%s' already exists", genre.name()));

        Genre g = Genre.builder()
                .name(genreName)
                .build();

        Genre saved = genreRepository.insert(g);
        return genreMapper.toDto(saved);
    }

    @Override
    public List<GenreResponse> getAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        return genres.stream()
                .map(genreMapper::toDto)
                .toList();
    }

    @Override
    public GenreResponse updateGenre(String id, GenreDto genre) {
        if (isGenreExists(genre.name()))
            throw new DuplicateException(String.format("Genre '%s' already exists", genre.name()));

        Genre g = getGenre(id);
        genreMapper.updateGenreFromDto(genre, g);

        return genreMapper.toDto(genreRepository.save(g));
    }

    @Override
    @Transactional
    public MessageResponse deleteGenre(String id) {
        Genre genre = getGenre(id);
        if (genre == null)
            return new MessageResponse("Genre with id '" + id + "' not found");

        genreRepository.delete(genre);
        movieRepository.removeGenreFromMovieGenres(id);
        return new MessageResponse("Genre with id '" + id + "' has been deleted");
    }
}
