package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.enums.RolesEnum;
import com.cofinprobootcamp.backend.enums.UserRights;
import com.cofinprobootcamp.backend.profile.Profile;
import com.cofinprobootcamp.backend.config.Regex;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

/**
 * This is plain data object representing the user of the skills platform,
 * i.e., it functions as their account information.
 * This class is an entity to be persisted into the database, the table
 * of persisted {@code User} objects is called "app_users".
 * <br>
 * {@code User} contains information about a user's email (unique identifier),
 * their status (locked or unlocked), their role (defining user rights) and
 * their profile (defined through class {@code Profile}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue
    Long id;

    //TODO: Schönere exception werfen falls email schon in use
    @NotBlank
    @Column(unique=true)
    @Pattern(regexp = Regex.VALID_MAIL_ADDRESS)
    private String email;

    /**
     * Locked users can't log in neither commit any actions.
     */
    private boolean locked = false; // Default value

//    @ManyToOne
//    private Role role;
    // For now as enum
    private RolesEnum role = RolesEnum.USER; // Default value

    @OneToOne
    @JoinColumn(unique = true)
    private Profile profile = null;

    public List<UserRights> getUserRights() {
        return this.role.getAllAssociatedUserRights();
    }
}
