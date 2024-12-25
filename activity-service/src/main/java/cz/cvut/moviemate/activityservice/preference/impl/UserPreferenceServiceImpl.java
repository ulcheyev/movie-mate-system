package cz.cvut.moviemate.activityservice.preference.impl;

import cz.cvut.moviemate.activityservice.preference.UserPreference;
import cz.cvut.moviemate.activityservice.preference.UserPreferenceDto;
import cz.cvut.moviemate.activityservice.preference.UserPreferenceRepository;
import cz.cvut.moviemate.activityservice.preference.UserPreferenceService;
import cz.cvut.moviemate.commonlib.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "USER_PREFERENCE_SERVICE")
@RequiredArgsConstructor
public class UserPreferenceServiceImpl implements UserPreferenceService {

    private final UserPreferenceRepository userPreferenceRepository;

    @Override
    @Cacheable(value = "preferences", key = "#username", unless = "#result.genresId.empty")
    public UserPreference getUserPreferences(String username) {
        log.info("Getting user preferences for {}", username);
        checkExists(username);

        return userPreferenceRepository.getUserPreferenceByUsername(username);
    }

    @Override
    @Cacheable(value = "preferences", key = "#username", unless = "#result.genresId.empty")
    public UserPreference save(String username, UserPreferenceDto dto) {
        log.info("Saving user preference {} {}", username, dto);

        UserPreference userPreference = UserPreference.builder()
                .username(username)
                .genresId(dto.genresId())
                .build();
        return userPreferenceRepository.save(userPreference);
    }

    @Override
    @CachePut(value = "preference", key = "#username")
    public UserPreference update(String username, UserPreferenceDto dto) {
        log.info("Updating user preference {} of user {}", dto, username);
        checkExists(username);

        UserPreference userPreference = UserPreference.builder()
                .username(username)
                .genresId(dto.genresId())
                .build();
        return userPreferenceRepository.save(userPreference);
    }

    private void checkExists(String username) {
        if (!userPreferenceRepository.existsById(username)) {
            log.warn("User preference of user {} not found", username);
            throw new NotFoundException("User preference of user " + username + " not found");
        }
    }
}
