package com.cofinprobootcamp.backend.skills;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill,Long> {
    public Optional<Skill> findSkillByName(String name);
}