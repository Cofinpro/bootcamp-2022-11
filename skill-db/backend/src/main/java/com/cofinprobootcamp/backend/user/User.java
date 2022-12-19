package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.profile.Profile;
import com.cofinprobootcamp.backend.role.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AppUser")
public class User {

    @Id
    @GeneratedValue
    Long id;

    //TODO: Schönere exception werfen falls email schon in use
    @NotBlank
    @Column(unique=true)
    @Pattern(regexp = "[\\w.]+@\\w+\\.\\w+")
    private String email;
    /**
     * Locked users can't log in neither commit any actions
     */
    private boolean locked;
    @ManyToOne
    private Role role;
    @OneToOne
    @JoinColumn(unique = true)
    private Profile profile;

}
