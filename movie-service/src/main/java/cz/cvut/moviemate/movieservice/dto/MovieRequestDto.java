package cz.cvut.moviemate.movieservice.dto;

import cz.cvut.moviemate.movieservice.dto.prop.CastDto;
import cz.cvut.moviemate.movieservice.dto.prop.DirectorDto;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public record MovieRequestDto(
        @NotBlank String title,
        List<String> genreIds,
        DirectorDto director,
        List<CastDto> casts,
        String synopsis,
        LocalDateTime releaseDate,
        String language
) implements Serializable {}