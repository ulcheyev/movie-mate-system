package cz.cvut.moviemate.movieservice.model.prop;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rating {

    @Field(name = "average")
    private Double average;

    @Field(name = "count")
    private Integer count;
}
