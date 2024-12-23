package cz.cvut.moviemate.movieservice.service.impl;

import cz.cvut.moviemate.commonlib.exception.DuplicateException;
import cz.cvut.moviemate.commonlib.exception.NotFoundException;
import cz.cvut.moviemate.movieservice.dto.*;
import cz.cvut.moviemate.movieservice.dto.mapper.BaseMapper;
import cz.cvut.moviemate.movieservice.dto.mapper.GenreMapper;
import cz.cvut.moviemate.movieservice.dto.mapper.MovieMapper;
import cz.cvut.moviemate.movieservice.dto.prop.GenreDto;
import cz.cvut.moviemate.movieservice.model.Genre;
import cz.cvut.moviemate.movieservice.model.Movie;
import cz.cvut.moviemate.movieservice.repository.GenreRepository;
import cz.cvut.moviemate.movieservice.repository.MovieRepository;
import cz.cvut.moviemate.movieservice.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "BASE_MOVIE_SERVICE")
@RequiredArgsConstructor
public class BaseMovieService implements MovieService {

    private final GenreMapper genreMapper;
    private final BaseMapper baseMapper;
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final MovieMapper movieMapper;

    private Genre getGenre(String id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Genre with id '%s' not found", id)));
    }

    private boolean isGenreExistsByName(String name) {
        return genreRepository.existsByName(name);
    }

    private Movie getMovieById(String id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Movie with id '%s' not found", id)));
    }

    private boolean isMovieExistsByTitle(String title) {
        return movieRepository.existsByTitle(title);
    }

    @Override
    @CacheEvict(value = "genres", key = "'all'")
    public List<GenreResponse> bulkSaveGenres(List<GenreDto> genres) {
        if (genres == null)
            throw new IllegalArgumentException("The genre list cannot be null");

        List<Genre> genresToSave = genres.stream()
                .filter(genreDto -> {
                    if (!isGenreExistsByName(genreDto.getName()))
                        return true;

                    log.warn("Genre with name '{}' already exists. Skipping...", genreDto.getName());
                    return false;
                })
                .map(genreMapper::fromDto)
                .toList();

        List<Genre> savedGenres = genreRepository.saveAll(genresToSave);
        return savedGenres.stream()
                .map(genreMapper::toDto)
                .toList();
    }

    @Override
    @CacheEvict(value = "genres", key = "'all'")
    public GenreResponse saveGenre(GenreDto genre) {
        String genreName = genre.getName();
        if (isGenreExistsByName(genreName))
            throw new DuplicateException(String.format("Genre '%s' already exists", genre.getName()));

        Genre g = genreMapper.fromDto(genre);

        Genre saved = genreRepository.insert(g);
        return genreMapper.toDto(saved);
    }

    @Override
    @Cacheable(value = "genres", key = "'all'")
    public List<GenreResponse> getAllGenres() {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");

        List<Genre> genres = genreRepository.findAll(sort);
        return genres.stream()
                .map(genreMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "genres", key = "'all'"),
                    @CacheEvict(value = "page", allEntries = true),
                    @CacheEvict(value = "movies", allEntries = true)
            }
    )
    @Transactional
    public MessageResponse deleteGenre(String id) {
        Genre genre = getGenre(id);

        genreRepository.delete(genre);
        movieRepository.removeGenreFromMovieGenres(id);
        return new MessageResponse(String.format("Genre with id '%s' has been deleted", id));
    }

    private Movie dtoToMovie(MovieRequestDto dto) {
        List<Genre> genres = dto.genreIds().stream()
                .map(this::getGenre)
                .toList();

        return movieMapper.fromDto(dto, genres);
    }

    @Override
    @CacheEvict(value = "page", allEntries = true)
    @Transactional
    public MovieDetailsDto saveMovie(MovieRequestDto movieDto) {
        if (isMovieExistsByTitle(movieDto.title()))
            throw new DuplicateException(String.format("Movie with title '%s' already exists", movieDto.title()));

        Movie movie = dtoToMovie(movieDto);
        return movieMapper.toDetailsDto(movieRepository.save(movie));
    }

    @Override
    @CacheEvict(value = "page", allEntries = true)
    @Transactional
    public List<MovieDetailsDto> bulkSaveMovie(List<MovieRequestDto> movies) {
        if (movies == null)
            throw new IllegalArgumentException("The movie list cannot be null");

        List<Movie> moviesToSave = movies.stream()
                .filter(movieDto -> {
                    if (!isMovieExistsByTitle(movieDto.title()))
                        return true;
                    log.warn("Movie with title '{}' already exists. Skipping...", movieDto.title());
                    return false;
                })
                .map(this::dtoToMovie)
                .toList();

        List<Movie> savedMovies = movieRepository.saveAll(moviesToSave);
        return savedMovies.stream()
                .map(movieMapper::toDetailsDto)
                .toList();
    }

    @Override
    @Cacheable(value = "movies", key = "#id")
    public MovieDetailsDto getMovie(String id) {
        Movie movie = getMovieById(id);
        return movieMapper.toDetailsDto(movie);
    }

    @Override
    @Cacheable(value = "movies-ids", key = "#ids")
    public List<MovieDetailsDto> getMovies(List<String> ids) {
        return movieRepository.findByIdIn(ids).stream().map(
                movieMapper::toDetailsDto
        ).toList();
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "movies", key = "#id"),
                    @CacheEvict(value = "page", allEntries = true),
                    @CacheEvict(value = "movies-ids", allEntries = true)
            }
    )
    public MessageResponse deleteMovie(String id) {
        Movie movie = getMovieById(id);

        movieRepository.delete(movie);
        return new MessageResponse(String.format("Genre with id %s has been deleted", id));
    }

    @Override
    @Caching(
            put = @CachePut(value = "movies", key = "#id"),
            evict = {
                    @CacheEvict(value = "page", allEntries = true),
                    @CacheEvict(value = "movies-ids", allEntries = true)
            }
    )
    public MovieDetailsDto updateMovie(String id, MovieRequestDto movieDto) {
        if (isMovieExistsByTitle(movieDto.title()))
            throw new DuplicateException(String.format("Movie with title '%s' already exists", movieDto.title()));

        Movie movie = getMovieById(id);

        movieMapper.updateMovieFromDto(movieDto, movie);
        return movieMapper.toDetailsDto(movieRepository.save(movie));
    }

    @Override
    @Cacheable(value = "page", keyGenerator = "pageKeyGenerator", unless = "#result.elements.size() < 4")
    public PageDto<MoviePreviewDto> getAllMovies(int pageNo, int pageSize, Sort.Direction order, String sortBy) {
        Sort sort = Sort.by(order, sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Movie> page = movieRepository.findAll(pageable);
        List<MoviePreviewDto> elements = page.getContent().stream()
                .map(movieMapper::toPreviewDto)
                .toList();

        return baseMapper.convertToPageDto(elements, pageNo, pageSize, page);
    }
}
