package cz.cvut.userservice.service.impl;

import cz.cvut.userservice.dto.AppUserDto;
import cz.cvut.userservice.dto.UpdateUserRequest;
import cz.cvut.userservice.dto.mapper.AppUserMapper;
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
import cz.cvut.userservice.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Slf4j(topic = "BASE_APP_USER_SERVICE")
@RequiredArgsConstructor
public class BaseAppUserService implements InternalAppUserService, ExternalAppUserService {
    private final AppUserMapper appUserMapper;
    private final AppUserRepository appUserRepository;
    private final UserHistoryRepository userHistoryRepository;
    private final UserRoleRepository userRoleRepository;
    private final ValidationUtil validationUtil;
    private final ObjectProvider<PasswordEncoder> passwordEncoderProvider;

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

    @Override
    @Transactional
    public AppUserDto getUserByUsername(String username, boolean details) {
        AppUser appUser = findUserByUsername(username);
        UserRole adminRole = findUserRoleByRole(Role.ADMIN);
        if (appUser.getRoles().contains(adminRole))
            return AppUserDto.builder().build();

        return hasDetails(appUser, details);
    }

    private AppUserDto hasDetails(AppUser appUser, boolean details) {
        if (details) {
            UserHistory userHistory = findUserHistoryById(appUser.getId());
            return appUserMapper.toDetailsDto(appUser, userHistory);
        }
        return appUserMapper.toDto(appUser);
    }

    @Override
    @Transactional
    public AppUserDto updateUser(String username, UpdateUserRequest request) {
        AppUser appUser = findUserByUsername(username);
        LocalDateTime now = LocalDateTime.now();

        validateUpdateRequest(request);
        appUserMapper.updateAppUserFromRequest(request, appUser);
        UserHistory userHistory = findUserHistoryById(appUser.getId());
        userHistory.setUpdatedAt(now);

        if (Objects.nonNull(request.password()) && !request.password().isEmpty()) {
            PasswordEncoder passwordEncoder = passwordEncoderProvider.getIfAvailable();
            assert passwordEncoder != null;
            String hashedPassword = passwordEncoder.encode(request.password());
            appUser.setPassword(hashedPassword);
        }

        AppUser saved = save(appUser);
        return appUserMapper.toDto(saved);
    }

    private void validateUpdateRequest(UpdateUserRequest request) {
        validationUtil.checkDuplicate(request.email(), this::findUserByEmail, "email");
    }
}
