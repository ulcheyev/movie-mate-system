package cz.cvut.userservice.dto.mapper;

import cz.cvut.userservice.dto.AppUserDetailsDto;
import cz.cvut.userservice.dto.AppUserDto;
import cz.cvut.userservice.dto.UpdateUserRequest;
import cz.cvut.userservice.model.AppUser;
import cz.cvut.userservice.model.UserHistory;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    @Mapping(target = "roles", expression = "java(rolesToString(appUser))")
    AppUserDto toDto(AppUser appUser);

    @Mapping(target = "roles", expression = "java(rolesToString(appUser))")
    @Mapping(target = "id", expression = "java(appUser.getId())")
    @Mapping(target = "userHistory", source = "userHistory")
    AppUserDetailsDto toDetailsDto(AppUser appUser, UserHistory userHistory);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "password", ignore = true)
    void updateAppUserFromRequest(UpdateUserRequest request, @MappingTarget AppUser appUser);

    default List<String> rolesToString(AppUser appUser) {
        return appUser.getRoles().stream()
                .map(role -> role.getRole().name())
                .toList();
    }
}
