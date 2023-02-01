package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.profile.Profile;
import com.cofinprobootcamp.backend.config.Regex;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.cofinprobootcamp.backend.role.StandardRoles;
import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
 * This is plain data object representing the user of the skills platform,
 * i.e., it functions as their account information.
 * This class is an entity to be persisted into the database, the table
 * of persisted {@link User} objects is called "app_users".
 * <br>
 * {@link User} contains information about a user's email (unique identifier),
 * their status (locked or unlocked), their role (defining user rights) and
 * their profile (defined through class {@link Profile}.
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

    @Column(unique = true, nullable = false)
    String outerId;

    /**
     * The User entity's username. Is always an email address.
     */
    @NotBlank
    @Column(unique=true)
    @Pattern(regexp = Regex.VALID_MAIL_ADDRESS)
    private String username;

    @NotBlank
    @Length(min = Regex.MINIMUM_PASSWORD_LENGTH)
    private String password;

    /**
     * Locked users can't log in neither commit any actions.
     */
    @Builder.Default
    private boolean locked = false; // Default value

    /**
     * The user's platform role which determines the accessible resources.
     */
    @Column(nullable = false)
    @Builder.Default
    private StandardRoles role = StandardRoles.USER; // Default value

    @OneToOne
    @JoinColumn(unique = true)
    @Builder.Default
    private Profile profile = null; // Default value

    @Override
    public String toString() {
        return String.format(
                "User[outerId: %s, user name: %s, locked: %b, role: %s, profileId: %s]",
                this.outerId,
                this.username,
                this.locked,
                this.role.toString(),
                profile != null ? profile.getOuterId() : ""
        );
    }
}
