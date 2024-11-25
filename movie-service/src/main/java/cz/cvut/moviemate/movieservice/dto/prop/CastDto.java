package cz.cvut.moviemate.movieservice.dto.prop;

import java.io.Serializable;

public record CastDto(
        String firstName,
        String lastName,
        String role
) implements Serializable {}