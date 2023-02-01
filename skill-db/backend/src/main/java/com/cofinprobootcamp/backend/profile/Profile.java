package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.enums.Expertises;
import com.cofinprobootcamp.backend.image.Image;
import com.cofinprobootcamp.backend.jobTitle.JobTitle;
import com.cofinprobootcamp.backend.skills.Skill;
import com.cofinprobootcamp.backend.user.User;
import javax.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.Set;

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

    @Column(unique = true, nullable = false)
    String outerId;

    private String firstName;
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jobtitle_id")
    private JobTitle jobTitle;

    private String phoneNumber;
    private String degree;
    private Expertises primaryExpertise;
    @Column(length = 5000)
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
    @Builder.Default
    private User owner = null; // Default value

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    @Builder.Default
    private Image profilePic = null;

    public int getAge() {
        return Period.between(this.birthDate, LocalDate.now()).getYears();
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    @Override
    public String toString() {
        return String.format(
                "Profile[outerId: %s, first name: %s, last name: %s, title: %s, owner: %s]",
                this.outerId,
                this.firstName,
                this.lastName,
                this.jobTitle.getName(),
                owner != null ? owner.getUsername() : ""
                );
    }
}
