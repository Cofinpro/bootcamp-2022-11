package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.profile.Profile;
import com.cofinprobootcamp.backend.role.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "AppUser")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    /**
     * unique identifier for each user
     */
    private String email;
    private String firstName;
    private String lastName;
    private Date birthDate;
    @Transient
    private int age;
    private String jobTitle;
    /**
     * User is set to locked by admin
     * User can't log in neither commit any actions
     */
    private boolean locked;
    @ManyToOne
    private Role role;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Profile> ownedProfiles;

    /**
     * User is allowed to edit those profiles (even if they are owned by a different user)
     */
    @ManyToMany(mappedBy = "editUsers", cascade = CascadeType.ALL)
    private List<Profile> editableProfiles;

    //TODO
    /**
     * Make sure profiles.get(0) is primary
     */
    @Transient
    private Profile primaryProfile;

    //TODO
    /**
     Is this needed?
     Discuss in Sprint 2.0
     */
    private boolean emailConfirmed;


    //TODO implement
    public int getAge() {
        return 0;
    }
}
