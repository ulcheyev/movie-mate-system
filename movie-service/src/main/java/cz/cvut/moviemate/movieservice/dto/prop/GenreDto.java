package cz.cvut.moviemate.movieservice.dto.prop;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record GenreDto(
        @NotBlank String name
) implements Serializable {}