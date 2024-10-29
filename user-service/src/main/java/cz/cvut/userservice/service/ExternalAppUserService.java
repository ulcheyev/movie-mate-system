package cz.cvut.userservice.service;

import cz.cvut.userservice.dto.AppUserDto;
import cz.cvut.userservice.dto.SetNewRolesRequest;
import cz.cvut.userservice.dto.UpdateUserRequest;

public interface ExternalAppUserService {

    AppUserDto getUserByUsername(String username, boolean details);

    AppUserDto updateUser(String username, UpdateUserRequest request);

    AppUserDto banUserByUsername(String username);

    AppUserDto unbanUserByUsername(String username);

    AppUserDto setNewRoles(String username, SetNewRolesRequest request);

    void deleteUserByUsername(String username);
}
