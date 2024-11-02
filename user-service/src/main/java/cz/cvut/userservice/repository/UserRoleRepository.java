package cz.cvut.userservice.repository;

import cz.cvut.userservice.model.Role;
import cz.cvut.userservice.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findByRole(Role role);
}
