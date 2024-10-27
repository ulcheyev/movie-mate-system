package cz.cvut.userservice.dto;

public record AuthorizationResponse(
        AppUserDto userDetails,
        String accessToken,
        String refreshToken
) {}
