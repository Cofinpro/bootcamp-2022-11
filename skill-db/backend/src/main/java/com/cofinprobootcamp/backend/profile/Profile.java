package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.enums.Expertises;
import com.cofinprobootcamp.backend.profile.dto.ProfileCreateInDTO;
import com.cofinprobootcamp.backend.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.LinkedList;
import java.util.List;
//TODO: jobtitle als enum oder datenbank damit wir ein drop-down draus machen können?
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    @GeneratedValue
    private Long id;
    private String jobTitle;
    private String degree;
    private Expertises primaryExpertise;
    private String referenceText;
    @ElementCollection
    private List<String> skills;

    @OneToOne
    private User owner;

    public Profile(ProfileCreateInDTO profileIn) {
        this.jobTitle = profileIn.jobTitle();
        this.degree = profileIn.degree();
        this.referenceText = profileIn.referenceText();

        this.skills = new LinkedList<>(profileIn.skills());
        this.primaryExpertise = Expertises.fromFullNameString(profileIn.primaryExpertise().toFullNameString());
        this.owner = null; //TODO: Implement logic for fetching user by email or throw error code
    }

}
