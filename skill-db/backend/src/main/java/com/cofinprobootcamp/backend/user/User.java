package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.profile.Profile;
import com.cofinprobootcamp.backend.role.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
// Warum brauchen wir überhaupt Id wenn wir email haben, die unique sein muss?
// (Im frontend) brauchen wir die auf jeden fall nicht
// Is locked somehow implemented in the security functions?
// Do we even need age variable if getter doesnt even use it?
// If removed, the getter should ofc not be named getXXX
@Data
@NoArgsConstructor
@Entity
@Table(name = "AppUser")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    /**
     * Unique identifier for each user
     */
    @NotBlank
    @Column(unique=true)
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    @NotNull
    private String jobTitle;
    @Transient
    private int age;

    /**
     * Locked users can't log in neither commit any actions
     */
    private boolean locked;
    @ManyToOne
    private Role role;
    @OneToOne
    private Profile profile;

    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
