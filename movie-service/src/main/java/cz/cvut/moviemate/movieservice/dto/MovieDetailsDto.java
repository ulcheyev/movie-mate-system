package cz.cvut.moviemate.movieservice.dto;

import cz.cvut.moviemate.movieservice.dto.prop.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDetailsDto implements Serializable {
    private String id;
    private String title;
    private List<GenreDto> genres;
    private DirectorDto director;
    private List<CastDto> casts;
    private String synopsis;
    private LocalDateTime releaseDate;
    private String language;
    private RatingDto rating;
    private List<ReviewDto> reviews;
}