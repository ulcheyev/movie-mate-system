package cz.cvut.userservice.repository;

import cz.cvut.userservice.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT soft_delete_app_user(:id)")
    void softDeleteUserById(Long id);
}
