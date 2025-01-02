package cz.cvut.moviemate.apigateway.client.dto;

import java.util.List;

public record AppUserDetails(
        String id,
        String username,
        String email,
        List<String> roles
) {
}