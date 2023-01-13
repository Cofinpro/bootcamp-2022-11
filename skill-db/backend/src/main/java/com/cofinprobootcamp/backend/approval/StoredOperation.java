package com.cofinprobootcamp.backend.approval;

import com.cofinprobootcamp.backend.user.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "stored_operation")
public class StoredOperation {

    @Id
    @GeneratedValue
    Long id;

    String operationPath;

    @ManyToOne
    @JoinColumn(name = "id")
    User user;

    String parameters;

    public Long getUserId() {
        return user.getId();
    }
}
