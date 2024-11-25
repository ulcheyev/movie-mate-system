package cz.cvut.moviemate.movieservice.model.prop;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Director {

    private String firstName;
    private String lastName;
}
