package cz.cvut.moviemate.movieservice.dto.mapper;

import cz.cvut.moviemate.commonlib.util.StringUtil;
import cz.cvut.moviemate.movieservice.dto.GenreResponse;
import cz.cvut.moviemate.movieservice.dto.prop.GenreDto;
import cz.cvut.moviemate.movieservice.model.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = StringUtil.class)
public interface GenreMapper {

    GenreResponse toDto(Genre genre);

    @Mapping(target = "id", expression = "java(StringUtil.toLowerCaseAndReplaceSpace(genreDto.getName()))")
    Genre fromDto(GenreDto genreDto);
}
