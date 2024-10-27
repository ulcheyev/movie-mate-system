package cz.cvut.userservice.dto;

import java.util.Set;

public record AppUserDto(
        Long id,
        String username,
        String email,
        String fullName,
        Set<UserRoleDto> roles,
        Boolean isEnabled,
        Boolean isNotBanned
) {}
