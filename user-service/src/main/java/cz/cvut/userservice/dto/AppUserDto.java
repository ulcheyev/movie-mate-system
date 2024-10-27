package cz.cvut.userservice.dto;

import java.util.List;

public record AppUserDto(
        Long id,
        String username,
        String email,
        String fullName,
        List<String> roles,
        Boolean isEnabled,
        Boolean isNotBanned
) {}
