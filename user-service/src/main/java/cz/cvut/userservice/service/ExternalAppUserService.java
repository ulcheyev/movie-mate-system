package cz.cvut.userservice.service;

import cz.cvut.userservice.dto.AppUserDto;
import cz.cvut.userservice.dto.PageDto;
import cz.cvut.userservice.dto.SetNewRolesRequest;
import cz.cvut.userservice.dto.UpdateUserRequest;
import org.springframework.data.domain.Sort;

public interface ExternalAppUserService {

    PageDto<AppUserDto> searchUsers(int pageNo, int pageSize, String sortBy, Sort.Direction order, String query, boolean details);

    AppUserDto getUserByUsername(String username, boolean details);

    AppUserDto updateUser(String username, UpdateUserRequest request);

    AppUserDto banUserByUsername(String username);

    AppUserDto unbanUserByUsername(String username);

    AppUserDto setNewRoles(String username, SetNewRolesRequest request);

    void deleteUserByUsername(String username);
}
