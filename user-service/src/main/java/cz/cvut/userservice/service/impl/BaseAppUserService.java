package cz.cvut.userservice.service.impl;

import cz.cvut.userservice.aspect.annotation.OnRootRestriction;
import cz.cvut.userservice.dto.AppUserDto;
import cz.cvut.userservice.dto.SetNewRolesRequest;
import cz.cvut.userservice.dto.UpdateUserRequest;
import cz.cvut.userservice.dto.mapper.AppUserMapper;
import cz.cvut.userservice.exception.NotFoundException;
import cz.cvut.userservice.model.AppUser;
import cz.cvut.userservice.model.Role;
import cz.cvut.userservice.model.UserHistory;
import cz.cvut.userservice.model.UserRole;
import cz.cvut.userservice.repository.AppUserRepository;
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
               .accountLocked(!appUser.getNotBanned())
               .disabled(!appUser.getEnabled())
               .build();
    }

    @Override
    @Transactional
    @OnRootRestriction
    public AppUserDto getUserByUsername(String username, boolean details) {
        AppUser appUser = findUserByUsername(username);

        return hasDetails(appUser, details);
    }

    private AppUserDto hasDetails(AppUser appUser, boolean details) {
        if (details) {
            UserHistory userHistory = appUser.getUserHistory();
            return appUserMapper.toDetailsDto(appUser, userHistory);
        }
        return appUserMapper.toDto(appUser);
    }

    @Override
    @Transactional
    public AppUserDto updateUser(String username, UpdateUserRequest request) {
        AppUser appUser = findUserByUsername(username);

        validateUpdateRequest(request);
        appUserMapper.updateAppUserFromRequest(request, appUser);
        UserHistory userHistory = appUser.getUserHistory();
        userHistory.setUpdatedAt(LocalDateTime.now());

        if (Objects.nonNull(request.password()) && !request.password().isEmpty()) {
            PasswordEncoder passwordEncoder = passwordEncoderProvider.getIfAvailable();
            assert passwordEncoder != null;
            String hashedPassword = passwordEncoder.encode(request.password());
            appUser.setPassword(hashedPassword);
        }

        appUser.setUserHistory(userHistory);
        AppUser saved = save(appUser);
        return appUserMapper.toDto(saved);
    }

    @Override
    @Transactional
    @OnRootRestriction
    public AppUserDto banUserByUsername(String username) {
        AppUser updated = banOrUnbanUser(username, true);
        return appUserMapper.toDto(updated);
    }

    @Override
    @Transactional
    @OnRootRestriction
    public AppUserDto unbanUserByUsername(String username) {
        AppUser updated = banOrUnbanUser(username, false);
        return appUserMapper.toDto(updated);
    }

    @Override
    @Transactional
    @OnRootRestriction
    public AppUserDto setNewRoles(String username, SetNewRolesRequest request) {
        AppUser appUser = findUserByUsername(username);
        Role[] rolesToAdd = request.roles();

        UserRole[] converted = new UserRole[rolesToAdd.length];
        for (int i = 0; i < request.roles().length; i++)
            converted[i] = findUserRoleByRole(rolesToAdd[i]);

        appUser.addRole(converted);
        AppUser saved = save(appUser);
        return appUserMapper.toDto(saved);
    }

    private AppUser banOrUnbanUser(String username, boolean isBan) {
        AppUser appUser = findUserByUsername(username);
        LocalDateTime now = LocalDateTime.now();

        UserHistory userHistory = appUser.getUserHistory();
        userHistory.setUpdatedAt(now);
        if (isBan) {
            appUser.setNotBanned(false);
            userHistory.setBannedAt(now);
        } else {
            appUser.setNotBanned(true);
            userHistory.setBannedAt(null);
        }

        appUser.setUserHistory(userHistory);
        return save(appUser);
    }

    @Override
    @Transactional
    @OnRootRestriction
    public void deleteUserByUsername(String username) {
        AppUser appUser = findUserByUsername(username);

        UserHistory userHistory = appUser.getUserHistory();
        userHistory.setUpdatedAt(LocalDateTime.now());
        appUser.setUserHistory(userHistory);
        appUser.setEnabled(false);
        AppUser updated = save(appUser);

        appUserRepository.softDeleteUserById(updated.getId());
        log.info("User {} was deleted softly.", username);
    }

    private void validateUpdateRequest(UpdateUserRequest request) {
        validationUtil.checkDuplicate(request.email(), this::findUserByEmail, "email");
    }
}
