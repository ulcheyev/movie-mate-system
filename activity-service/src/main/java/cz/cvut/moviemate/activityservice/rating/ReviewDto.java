package cz.cvut.moviemate.activityservice.rating;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;
import java.util.Set;

public record ReviewDto  (

     @NotNull(message = "Rate cannot be blank.")
     @Range(min = 1, max = 5, message = "Rate can have min 1 and max 5 stars.")
     int rate,

     @Size(min = 0, max = 1500, message = "Review text can have max 1500 characters.")
     String reviewText,

     @NotBlank(message = "Movie id cannot be blank.")
     String movieId,

     Instant timestamp,
     Set<String> tags,
     Map<String, String> metadata

) implements Serializable {}
