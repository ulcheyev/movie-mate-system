package cz.cvut.moviemate.movieservice.dto;

import java.io.Serializable;

public record MessageResponse(
        String message
) implements Serializable {
}
