package com.cofinprobootcamp.backend.skills;

import com.cofinprobootcamp.backend.profile.Profile;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Skill {
    @Id
    @GeneratedValue
    @Column(name="skill_id")
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "skillSet")
    Set<Profile> profileSet;
    public Skill(String name) {
        this.name = name;
    }
}
