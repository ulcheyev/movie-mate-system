package cz.cvut.moviemate.movieservice.dto.prop;

import java.io.Serializable;

public record ReviewDto(
        String user,
        String comment,
        Integer rating
) implements Serializable {}