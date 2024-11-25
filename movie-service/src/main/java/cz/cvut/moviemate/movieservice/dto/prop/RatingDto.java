package cz.cvut.moviemate.movieservice.dto.prop;

import java.io.Serializable;

public record RatingDto(
        Double average,
        Integer count
) implements Serializable {}