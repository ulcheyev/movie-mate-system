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
public class CastDto implements Serializable {
    private String firstName;
    private String lastName;
    private String role;
}