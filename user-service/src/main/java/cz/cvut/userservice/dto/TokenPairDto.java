package cz.cvut.userservice.dto;

import java.io.Serializable;

public record TokenPairDto(
        String accessToken,
        String refreshToken
) implements Serializable {}
