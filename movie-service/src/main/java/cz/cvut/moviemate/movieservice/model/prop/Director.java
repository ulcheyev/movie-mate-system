package cz.cvut.moviemate.movieservice.model.prop;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Director {

    @Field(name = "firstName")
    private String firstName;

    @Field(name = "lastName")
    private String lastName;
}
