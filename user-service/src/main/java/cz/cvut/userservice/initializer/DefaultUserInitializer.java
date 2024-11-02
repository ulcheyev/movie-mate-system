package cz.cvut.userservice.initializer;

import cz.cvut.userservice.exception.NotFoundException;
import cz.cvut.userservice.model.AppUser;
import cz.cvut.userservice.model.Role;
import cz.cvut.userservice.model.UserRole;
import cz.cvut.userservice.service.InternalAppUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j(topic = "DEFAULT_USER_INITIALIZER")
@RequiredArgsConstructor
public class DefaultUserInitializer {
    private final InternalAppUserService internalAppUserService;
    private final PasswordEncoder passwordEncoder;

    @Value("${security.root-user.username}")
    private String rootUsername;

    @Value("${security.root-user.email}")
    private String rootEmail;

    @Value("${security.root-user.password}")
    private String rootPassword;

    @Bean
    public CommandLineRunner init() {
        try {
            if (internalAppUserService.findUserByUsername(rootUsername) != null)
                return args -> {};
        } catch (NotFoundException ignored) {}

        String hashedPassword = passwordEncoder.encode(rootPassword);
        return args -> {
            AppUser appUser = AppUser.builder()
                    .username(rootUsername)
                    .email(rootEmail)
                    .password(hashedPassword)
                    .build();
            appUser.addRole(initializeRootRole());

            internalAppUserService.save(appUser);
            log.info("Root user is initialized successfully!");
        };
    }

    private UserRole[] initializeRootRole() {
        UserRole[] roles = new UserRole[Role.values().length];
        for (int i = 0; i < roles.length; i++)
            roles[i] = internalAppUserService.findUserRoleByRole(Role.values()[i]);

        return roles;
    }
}
