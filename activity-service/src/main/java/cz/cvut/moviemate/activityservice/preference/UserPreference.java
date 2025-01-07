package cz.cvut.moviemate.activityservice.preference;

import lombok.*;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Set;

@Table("user_preference")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPreference {

    @PrimaryKey
    @PrimaryKeyColumn(name = "username", type = PrimaryKeyType.PARTITIONED)
    private String username;
    private Set<String> genresId;
}
