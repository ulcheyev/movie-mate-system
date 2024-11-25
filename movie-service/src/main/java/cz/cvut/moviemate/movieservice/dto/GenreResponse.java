package cz.cvut.moviemate.movieservice.dto;

import java.io.Serializable;

public record GenreResponse(
        String id,
        String name
) implements Serializable {}
