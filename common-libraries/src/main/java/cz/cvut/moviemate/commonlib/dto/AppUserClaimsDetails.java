package cz.cvut.moviemate.commonlib.dto;

import java.util.List;

public record AppUserClaimsDetails(
        String id,
        String username,
        String email,
        List<String> roles,
        String token
) {
}