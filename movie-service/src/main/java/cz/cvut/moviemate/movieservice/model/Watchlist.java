package cz.cvut.moviemate.movieservice.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "watchlists")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Watchlist {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @Field(name = "user_id")
    private Long userId;

    @Field(name = "name")
    private String name;

    @Field(name = "movies")
    private List<String> movies;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Field(name = "date_created")
    @Builder.Default
    private LocalDateTime dateCreated = LocalDateTime.now();
}
