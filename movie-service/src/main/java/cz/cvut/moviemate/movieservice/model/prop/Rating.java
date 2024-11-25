package cz.cvut.moviemate.movieservice.model.prop;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rating {

    private Double average;
    private Integer count;
}
