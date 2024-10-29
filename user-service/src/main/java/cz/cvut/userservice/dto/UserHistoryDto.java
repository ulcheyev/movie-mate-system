package cz.cvut.userservice.dto;

import java.time.LocalDateTime;

public record UserHistoryDto(
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,
        LocalDateTime bannedAt
) {}
