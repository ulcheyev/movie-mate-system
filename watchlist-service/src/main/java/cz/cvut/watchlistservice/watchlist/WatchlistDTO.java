package cz.cvut.watchlistservice.watchlist;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WatchlistDTO {

    private String id;

    @Size(max = 100, message = "Watchlist name cannot exceed 100 characters")
    private String name;

    private List<String> moviesId;

    private LocalDate dateCreated;
}