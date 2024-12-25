package cz.cvut.moviemate.activityservice.preference;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPreferenceRepository extends CassandraRepository<UserPreference, String> {
    UserPreference getUserPreferenceByUsername(String username);
}
