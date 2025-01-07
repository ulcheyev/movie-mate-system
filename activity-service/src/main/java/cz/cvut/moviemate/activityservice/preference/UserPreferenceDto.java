package cz.cvut.moviemate.activityservice.preference;

import java.io.Serializable;
import java.util.HashSet;

public record UserPreferenceDto(
        HashSet<String> genresId
) implements Serializable {}