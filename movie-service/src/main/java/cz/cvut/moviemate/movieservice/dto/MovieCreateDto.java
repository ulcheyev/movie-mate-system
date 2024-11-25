package cz.cvut.moviemate.movieservice.dto;

import cz.cvut.moviemate.movieservice.dto.prop.CastDto;
import cz.cvut.moviemate.movieservice.dto.prop.DirectorDto;
import cz.cvut.moviemate.movieservice.dto.prop.GenreDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public record MovieCreateDto(
        String title,
        List<GenreDto> genres,
        DirectorDto director,
        List<CastDto> casts,
        String synopsis,
        LocalDateTime releaseDate,
        String language
) implements Serializable {}