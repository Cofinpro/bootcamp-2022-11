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
