package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.enums.Expertises;
import com.cofinprobootcamp.backend.jobTitle.JobTitle;
import com.cofinprobootcamp.backend.skills.Skill;
import com.cofinprobootcamp.backend.user.User;
import javax.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.Set;

//TODO: jobtitle als enum oder datenbank damit wir ein drop-down draus machen können?
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jobtitle_id")
    private JobTitle jobTitle;
    private String phoneNumber;
    private String degree;
    private Expertises primaryExpertise;
    private String referenceText;

    @ManyToMany
    @JoinTable(
            name ="profile_skill",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skillSet;
    private LocalDate birthDate;

    @OneToOne
    @JoinColumn(unique = true)
    private User owner;

    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
