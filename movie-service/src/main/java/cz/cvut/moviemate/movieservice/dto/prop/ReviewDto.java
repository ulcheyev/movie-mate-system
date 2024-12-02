package cz.cvut.moviemate.movieservice.dto.prop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto implements Serializable {
    private String user;
    private String comment;
    private Integer rating;
}