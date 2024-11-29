package cz.cvut.moviemate.movieservice.dto.mapper;

import cz.cvut.moviemate.movieservice.dto.GenreResponse;
import cz.cvut.moviemate.movieservice.dto.prop.GenreDto;
import cz.cvut.moviemate.movieservice.model.Genre;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    GenreResponse toDto(Genre genre);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateGenreFromDto(GenreDto dto, @MappingTarget Genre genre);
}
