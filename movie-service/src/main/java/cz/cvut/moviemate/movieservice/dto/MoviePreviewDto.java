package cz.cvut.moviemate.movieservice.dto;

import cz.cvut.moviemate.movieservice.dto.prop.GenreDto;
import cz.cvut.moviemate.movieservice.dto.prop.RatingDto;
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
public class MoviePreviewDto implements Serializable {
    private String id;
    private String title;
    private List<GenreDto> genres;
    private LocalDateTime releaseDate;
    private RatingDto rating;
}
