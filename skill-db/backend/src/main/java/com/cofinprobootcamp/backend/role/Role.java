package com.cofinprobootcamp.backend.role;

import com.cofinprobootcamp.backend.enums.UserRights;
import com.cofinprobootcamp.backend.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ElementCollection
    private List<UserRights> userRights;

    //TODO
    /**
     * Discuss which cascade type
     */
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<User> users;


}
