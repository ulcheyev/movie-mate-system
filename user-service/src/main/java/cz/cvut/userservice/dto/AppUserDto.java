package cz.cvut.userservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class AppUserDto {
    private final Long id;
    private final String username;
    private final String email;
    private final String fullName;
    private final List<String> roles;
    private final Boolean isEnabled;
    private final Boolean isNotBanned;
}