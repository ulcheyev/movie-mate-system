package cz.cvut.moviemate.movieservice.dto.mapper;

import cz.cvut.moviemate.movieservice.dto.MovieDetailsDto;
import cz.cvut.moviemate.movieservice.dto.MoviePreviewDto;
import cz.cvut.moviemate.movieservice.dto.MovieRequestDto;
import cz.cvut.moviemate.movieservice.dto.prop.CastDto;
import cz.cvut.moviemate.movieservice.dto.prop.DirectorDto;
import cz.cvut.moviemate.movieservice.model.Genre;
import cz.cvut.moviemate.movieservice.model.Movie;
import cz.cvut.moviemate.movieservice.model.prop.Cast;
import cz.cvut.moviemate.movieservice.model.prop.Director;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateMovieFromDto(MovieRequestDto dto, @MappingTarget Movie movie);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "genres", expression = "java(genres)")
    Movie fromDto(MovieRequestDto movieRequestDto, List<Genre> genres);

    Director fromDto(DirectorDto directorDto);

    Cast fromDto(CastDto castDto);

    MovieDetailsDto toDetailsDto(Movie movie);

    MoviePreviewDto toPreviewDto(Movie movie);
}
