package cz.cvut.moviemate.commonlib.dto.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchlistEditEvent {

    private List<String> movieIds;
    private String userId;


}
