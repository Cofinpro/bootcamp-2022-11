package com.cofinprobootcamp.backend.role;

import com.cofinprobootcamp.backend.enums.UserNamespaceRights;
import com.cofinprobootcamp.backend.enums.UserOperationRights;
import com.cofinprobootcamp.backend.enums.UserScopeRights;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "user_right"
)
public class UserRight {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "namespace")
    private UserNamespaceRights namespace;

    @Column(name = "operation")
    private UserOperationRights operation;

    @Column(name = "scope")
    private UserScopeRights scope;
}
