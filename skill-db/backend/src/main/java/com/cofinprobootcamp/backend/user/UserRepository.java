package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.role.StandardRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findFirstByOuterId(String outerId);

    List<User> findAllByRole(StandardRoles role);

    long deleteByOuterId(String outerId);
}
