package cz.cvut.moviemate.activityservice.preference;

import cz.cvut.moviemate.commonlib.utils.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/preferences")
public class UserPreferenceController {

    private final UserPreferenceService userPreferenceService;

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserPreference getUserPreferences(@PathVariable String username) {
        return userPreferenceService.getUserPreferences(username);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserPreference createUserPreference(@RequestBody @Valid UserPreferenceDto dto) {
        return userPreferenceService.save(getUsername(), dto);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserPreference updateUserPreference(@RequestBody @Valid UserPreferenceDto dto) {
        return userPreferenceService.update(getUsername(), dto);
    }

    private String getUsername() {
        return SecurityUtils.getPrincipalUsername(SecurityContextHolder.getContext().getAuthentication());
    }
}
