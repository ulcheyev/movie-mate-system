package cz.cvut.userservice.service.impl;

import cz.cvut.userservice.exception.NotFoundException;
import cz.cvut.userservice.model.AppUser;
import cz.cvut.userservice.model.Role;
import cz.cvut.userservice.model.UserHistory;
import cz.cvut.userservice.model.UserRole;
import cz.cvut.userservice.repository.AppUserRepository;
import cz.cvut.userservice.repository.UserHistoryRepository;
import cz.cvut.userservice.repository.UserRoleRepository;
import cz.cvut.userservice.service.ExternalAppUserService;
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
public class BaseAppUserService implements InternalAppUserService, ExternalAppUserService {
    private final AppUserRepository appUserRepository;
    private final UserHistoryRepository userHistoryRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public AppUser findUserByUsername(String username) {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(String.format("Username %s not found", username)));
    }

    @Override
    public AppUser findUserByEmail(String email) {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(String.format("Email %s not found", email)));
    }

    @Override
    public AppUser save(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    @Override
    public UserHistory findUserHistoryById(Long id) {
        return userHistoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User's history with ID: %s not found", id)));
    }

    @Override
    public void updateUserHistory(UserHistory userHistory) {
        userHistoryRepository.save(userHistory);
    }

    @Override
    public UserRole findUserRoleByRole(Role role) {
        return userRoleRepository.findByRole(role)
                .orElseThrow(() -> new NotFoundException(String.format("Role %s not found", role.name())));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       final AppUser appUser = findUserByUsername(username);
       return User.builder()
               .username(appUser.getUsername())
               .password(appUser.getPassword())
               .authorities(appUser.getAuthorities())
               .accountLocked(!appUser.getIsNotBanned())
               .build();
    }
}
