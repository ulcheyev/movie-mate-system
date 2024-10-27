package cz.cvut.userservice.dto.mapper;

import cz.cvut.userservice.dto.AppUserClaims;
import cz.cvut.userservice.dto.AppUserDto;
import cz.cvut.userservice.model.AppUser;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-27T21:54:20+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Ubuntu)"
)
@Component
public class AppUserMapperImpl implements AppUserMapper {

    @Override
    public AppUserDto toDto(AppUser appUser) {
        if ( appUser == null ) {
            return null;
        }

        Long id = null;
        String username = null;
        String email = null;
        String fullName = null;
        Boolean isEnabled = null;
        Boolean isNotBanned = null;

        id = appUser.getId();
        username = appUser.getUsername();
        email = appUser.getEmail();
        fullName = appUser.getFullName();
        isEnabled = appUser.getIsEnabled();
        isNotBanned = appUser.getIsNotBanned();

        List<String> roles = rolesToString(appUser);

        AppUserDto appUserDto = new AppUserDto( id, username, email, fullName, roles, isEnabled, isNotBanned );

        return appUserDto;
    }

    @Override
    public AppUserClaims toAppUserClaims(AppUser appUser) {
        if ( appUser == null ) {
            return null;
        }

        String username = null;
        String email = null;

        username = appUser.getUsername();
        email = appUser.getEmail();

        List<String> roles = rolesToString(appUser);

        AppUserClaims appUserClaims = new AppUserClaims( username, email, roles );

        return appUserClaims;
    }
}
