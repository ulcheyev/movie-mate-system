package cz.cvut.userservice.dto;

import java.util.List;

public record AppUserClaims(
        String username,
        String email,
        List<String> roles
) {}
