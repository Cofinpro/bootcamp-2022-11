package com.cofinprobootcamp.backend.role;

import com.cofinprobootcamp.backend.enums.UserRights;
import com.cofinprobootcamp.backend.user.User;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    @Column(unique = true)
    private String name;
    @ElementCollection
    private List<UserRights> userRights;

    //TODO Set correct cascade type
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<User> users;


}
