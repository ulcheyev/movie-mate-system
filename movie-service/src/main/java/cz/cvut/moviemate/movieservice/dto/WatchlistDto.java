package cz.cvut.moviemate.movieservice.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public record WatchlistDto(
        String id,
        Long userId,
        String name,
        List<String> movies,
        LocalDateTime dateCreated
) implements Serializable {}