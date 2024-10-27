package cz.cvut.userservice.service;

import cz.cvut.userservice.model.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface InternalAppUserService extends UserDetailsService {

    AppUser findByUsername(String username);
}
