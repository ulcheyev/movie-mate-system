package cz.cvut.moviemate.movieservice.service.impl;

import cz.cvut.moviemate.commonlib.exception.DuplicateException;
import cz.cvut.moviemate.commonlib.exception.NotFoundException;
import cz.cvut.moviemate.movieservice.dto.GenreResponse;
import cz.cvut.moviemate.movieservice.dto.mapper.GenreMapper;
import cz.cvut.moviemate.movieservice.dto.prop.GenreDto;
import cz.cvut.moviemate.movieservice.model.Genre;
import cz.cvut.moviemate.movieservice.model.Movie;
import cz.cvut.moviemate.movieservice.repository.GenreRepository;
import cz.cvut.moviemate.movieservice.repository.MovieRepository;
import cz.cvut.moviemate.movieservice.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
    private final MongoTemplate mongoTemplate;

    private Genre getGenre(String id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Genre with id '%s' not found", id)));
    }

    private boolean isGenreExists(String name) {
        return genreRepository.existsByName(name);
    }

    @Override
    public List<GenreResponse> bulkSaveGenres(List<GenreDto> genres) {
        return List.of();
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
        return null;
    }

    @Override
    @Transactional
    public void deleteGenre(String id) {
        Genre genre = getGenre(id);
        if (genre == null) return;

        genreRepository.delete(genre);

        Query query = new Query(Criteria.where("genre._id").is(new ObjectId(id)));
        Update update = new Update().pull("genres", Query.query(Criteria.where("_id").is(new ObjectId(id))));
        mongoTemplate.updateMulti(query, update, Movie.class);
    }
}
