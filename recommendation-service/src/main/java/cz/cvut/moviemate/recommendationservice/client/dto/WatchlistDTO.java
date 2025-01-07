package cz.cvut.moviemate.recommendationservice.client.dto;

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

    private String name;

    private List<String> moviesId;

    private LocalDate dateCreated;
}