package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.enums.Expertises;
import com.cofinprobootcamp.backend.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
//TODO: jobtitle als enum oder datenbank damit wir ein drop-down draus machen können?
@Entity
@Data
public class Profile {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String jobTitle;
    @NotNull
    private String degree;
    @NotNull
    private Expertises primaryExpertise;
    @NotNull
    private String referenceText;

    @ElementCollection
    private List<String> skills;

    @OneToOne
    private User owner;

}
