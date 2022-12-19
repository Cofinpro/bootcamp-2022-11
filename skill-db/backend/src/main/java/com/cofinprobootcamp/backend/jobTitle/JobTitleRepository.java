package com.cofinprobootcamp.backend.jobTitle;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobTitleRepository extends JpaRepository<JobTitle, Long> {
    Optional<JobTitle> findJobTitleByName(String name);
}
