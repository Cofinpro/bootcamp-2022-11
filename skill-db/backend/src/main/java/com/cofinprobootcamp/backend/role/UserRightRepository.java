package com.cofinprobootcamp.backend.role;

import com.cofinprobootcamp.backend.enums.UserNamespaceRights;
import com.cofinprobootcamp.backend.enums.UserOperationRights;
import com.cofinprobootcamp.backend.enums.UserScopeRights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRightRepository extends JpaRepository<UserRight, Long> {
    Optional<UserRight> findUserRightByNamespaceAndOperationAndScope(UserNamespaceRights namespace, UserOperationRights operation, UserScopeRights scope);
}
