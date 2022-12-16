package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.enums.Expertises;
import com.cofinprobootcamp.backend.profile.dto.ProfileCreateInDTO;
import com.cofinprobootcamp.backend.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;
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
    private String firstName;
    private String lastName;
    private String jobTitle;
    private String phoneNumber;
    private String degree;
    private Expertises primaryExpertise;
    private String referenceText;
    @ElementCollection
    private List<String> skills;
    private LocalDate birthDate;
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
    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

}
