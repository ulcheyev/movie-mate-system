package cz.cvut.userservice.dto.mapper;

import cz.cvut.userservice.dto.UserHistoryDto;
import cz.cvut.userservice.model.UserHistory;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-28T23:56:37+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Ubuntu)"
)
@Component
public class UserHistoryMapperImpl implements UserHistoryMapper {

    @Override
    public UserHistoryDto toDto(UserHistory userHistory) {
        if ( userHistory == null ) {
            return null;
        }

        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;
        LocalDateTime deletedAt = null;
        LocalDateTime bannedAt = null;

        createdAt = userHistory.getCreatedAt();
        updatedAt = userHistory.getUpdatedAt();
        deletedAt = userHistory.getDeletedAt();
        bannedAt = userHistory.getBannedAt();

        UserHistoryDto userHistoryDto = new UserHistoryDto( createdAt, updatedAt, deletedAt, bannedAt );

        return userHistoryDto;
    }
}
