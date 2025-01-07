package cz.cvut.moviemate.activityservice.preference;

public interface UserPreferenceService {

    UserPreference getUserPreferences(String username);

    UserPreference save(String username, UserPreferenceDto dto);

    UserPreference update(String username, UserPreferenceDto dto);
}
