package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findProfileByOwner(User Owner);

    Optional<Profile> findFirstByOuterId(String outerId);

    void deleteByOuterId(String outerId);
}
