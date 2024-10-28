package cz.cvut.userservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Table
@Entity(name = "roles")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_seq_gen")
    @SequenceGenerator(name = "roles_seq_gen", sequenceName = "roles_id_seq", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @NotBlank
    @Pattern(regexp = "^[A-Z_]+$", message = "Role can only contain uppercase letters and underscores.")
    private Role role;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<AppUser> users = new HashSet<>();
}