package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.enums.Expertises;
import com.cofinprobootcamp.backend.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Profile {

    @Id
    @GeneratedValue
    private Long id;
    private String jobTitle;
    private String degree;
    private Expertises primaryExpertise;
    private String referenceText;
    private float version;
    private boolean archived;
    /**
     * Can be edited by all users
     */
    private boolean isOpen;

    @ManyToMany
    private List<User> editUsers;

    @ElementCollection
    private List<String> skills;

    @ManyToOne
    private User owner;

}
