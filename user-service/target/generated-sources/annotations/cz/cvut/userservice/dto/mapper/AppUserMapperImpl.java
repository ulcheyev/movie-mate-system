package cz.cvut.userservice.dto.mapper;

import cz.cvut.userservice.dto.AppUserDetailsDto;
import cz.cvut.userservice.dto.AppUserDto;
import cz.cvut.userservice.dto.UpdateUserRequest;
import cz.cvut.userservice.dto.UserHistoryDto;
import cz.cvut.userservice.model.AppUser;
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
public class AppUserMapperImpl implements AppUserMapper {

    @Override
    public AppUserDto toDto(AppUser appUser) {
        if ( appUser == null ) {
            return null;
        }

        AppUserDto.AppUserDtoBuilder<?, ?> appUserDto = AppUserDto.builder();

        appUserDto.id( appUser.getId() );
        appUserDto.username( appUser.getUsername() );
        appUserDto.email( appUser.getEmail() );
        appUserDto.fullName( appUser.getFullName() );

        appUserDto.roles( rolesToString(appUser) );

        return appUserDto.build();
    }

    @Override
    public AppUserDetailsDto toDetailsDto(AppUser appUser, UserHistory userHistory) {
        if ( appUser == null && userHistory == null ) {
            return null;
        }

        AppUserDetailsDto.AppUserDetailsDtoBuilder<?, ?> appUserDetailsDto = AppUserDetailsDto.builder();

        if ( appUser != null ) {
            appUserDetailsDto.username( appUser.getUsername() );
            appUserDetailsDto.email( appUser.getEmail() );
            appUserDetailsDto.fullName( appUser.getFullName() );
        }
        appUserDetailsDto.userHistory( userHistoryToUserHistoryDto( userHistory ) );
        appUserDetailsDto.roles( rolesToString(appUser) );
        appUserDetailsDto.id( appUser.getId() );

        return appUserDetailsDto.build();
    }

    @Override
    public void updateAppUserFromRequest(UpdateUserRequest request, AppUser appUser) {
        if ( request == null ) {
            return;
        }

        if ( request.email() != null ) {
            appUser.setEmail( request.email() );
        }
        if ( request.fullName() != null ) {
            appUser.setFullName( request.fullName() );
        }
    }

    protected UserHistoryDto userHistoryToUserHistoryDto(UserHistory userHistory) {
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
