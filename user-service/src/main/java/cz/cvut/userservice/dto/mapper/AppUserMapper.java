package cz.cvut.userservice.dto.mapper;

import cz.cvut.userservice.dto.AppUserClaims;
import cz.cvut.userservice.dto.AppUserDto;
import cz.cvut.userservice.model.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    @Mapping(target = "roles", expression = "java(rolesToString(appUser))")
    AppUserDto toDto(AppUser appUser);

    @Mapping(target = "roles", expression = "java(rolesToString(appUser))")
    AppUserClaims toAppUserClaims(AppUser appUser);

    default List<String> rolesToString(AppUser appUser) {
        return appUser.getRoles().stream()
                .map(role -> role.getRole().name())
                .toList();
    }
}
