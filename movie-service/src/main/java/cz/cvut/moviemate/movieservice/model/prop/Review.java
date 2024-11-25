package cz.cvut.moviemate.movieservice.model.prop;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    private String user;
    private String comment;
    private Integer rating;
}
