package com.cofinprobootcamp.backend.skills;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill,Long> {
    @Query(value = "SELECT * FROM skill WHERE lower(name)= lower(:searchByName) ",nativeQuery = true)
    public Optional<Skill> findSkillByName(@Param("searchByName") String name);
}
