package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.profile.Profile;
import com.cofinprobootcamp.backend.role.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank
    @Pattern(regexp = "\\d+")
    private String phoneNumber;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
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
