package cz.cvut.userservice.service;

import cz.cvut.userservice.model.AppUser;
import cz.cvut.userservice.model.Role;
import cz.cvut.userservice.model.UserRole;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface InternalAppUserService extends UserDetailsService {

    AppUser findUserByUsername(String username);

    AppUser findUserByEmail(String email);

    AppUser save(AppUser appUser);

    UserRole findUserRoleByRole(Role role);
}
