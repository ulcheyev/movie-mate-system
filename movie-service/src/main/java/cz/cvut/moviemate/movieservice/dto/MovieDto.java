package cz.cvut.moviemate.movieservice.dto;

import cz.cvut.moviemate.movieservice.dto.prop.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public record MovieDto(
        String id,
        String title,
        List<GenreDto> genres,
        DirectorDto director,
        List<CastDto> casts,
        String synopsis,
        LocalDateTime releaseDate,
        String language,
        RatingDto rating,
        List<ReviewDto> reviews
) implements Serializable {}