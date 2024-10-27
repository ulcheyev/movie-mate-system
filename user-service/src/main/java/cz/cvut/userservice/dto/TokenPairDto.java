package cz.cvut.userservice.dto;

public record TokenPairDto(
        String accessToken,
        String refreshToken
) {}
