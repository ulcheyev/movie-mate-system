package cz.cvut.moviemate.movieservice.model;

import cz.cvut.moviemate.movieservice.model.prop.Cast;
import cz.cvut.moviemate.movieservice.model.prop.Director;
import cz.cvut.moviemate.movieservice.model.prop.Rating;
import cz.cvut.moviemate.movieservice.model.prop.Review;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "movies")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    private String id;

    @Field(name = "title")
    @Indexed
    private String title;

    @Field(name = "genres")
    private List<Genre> genres;

    @Field(name = "director")
    private Director director;

    @Field(name = "casts")
    private List<Cast> casts;

    @Field(name = "synopsis")
    private String synopsis;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Field(name = "release_date")
    private LocalDateTime releaseDate;

    @Field(name = "language")
    private String language;

    @Field(name = "rating")
    @Builder.Default
    private Rating rating = new Rating(0.0, 0);

    @Field(name = "reviews")
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();
}
