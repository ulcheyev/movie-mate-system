package cz.cvut.userservice.service.impl;

import cz.cvut.userservice.exception.NotFoundException;
import cz.cvut.userservice.model.AppUser;
import cz.cvut.userservice.repository.AppUserRepository;
import cz.cvut.userservice.service.InternalAppUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "BASE_APP_USER_SERVICE")
@RequiredArgsConstructor
public class BaseAppUserService implements InternalAppUserService {
    private final AppUserRepository appUserRepository;

    @Override
    public AppUser findByUsername(String username) {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(String.format("Username %s not found", username)));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       final AppUser appUser = findByUsername(username);
       return User.builder()
               .username(appUser.getUsername())
               .password(appUser.getPassword())
               .authorities(appUser.getAuthorities())
               .accountLocked(!appUser.getIsNotBanned())
               .build();
    }
}
