package cz.cvut.watchlistservice.watchlist;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "watchlists")
public class Watchlist {

    @Id
    private String id;

    private String username;

    private String name;

    private List<String> moviesId;

    private LocalDate dateCreated;
}
