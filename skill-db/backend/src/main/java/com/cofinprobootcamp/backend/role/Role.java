package com.cofinprobootcamp.backend.role;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.*;

import javax.persistence.Entity;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @NotBlank
    private String descriptiveName;

    @ManyToMany
    private List<UserRight> userRights;

//    //TODO Set correct cascade type
//    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
//    private List<User> users;
    // --> is this even needed?
}
