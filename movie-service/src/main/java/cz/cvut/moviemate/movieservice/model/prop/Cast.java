package cz.cvut.moviemate.movieservice.model.prop;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cast {

    private String firstName;
    private String lastName;
    private String role;
}
