package cz.cvut.moviemate.movieservice.dto.prop;

import java.io.Serializable;

public record DirectorDto(
        String firstName,
        String lastName
) implements Serializable {}