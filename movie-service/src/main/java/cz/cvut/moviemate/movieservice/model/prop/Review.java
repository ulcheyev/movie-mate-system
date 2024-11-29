package cz.cvut.moviemate.movieservice.model.prop;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Field(name = "user")
    private String user;

    @Field(name = "comment")
    private String comment;

    @Field(name = "rating")
    private Integer rating;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Field(name = "date_created")
    @Builder.Default
    private LocalDateTime dateCreated = LocalDateTime.now();
}
