package com.cofinprobootcamp.backend.skills;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/skills")
public class SkillController {
    SkillRepository skillRepository;
    public SkillController(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }
    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_HR')")
    public List<String> getSkills() {
        return skillRepository
                .findAll()
                .stream()
                .map(Skill::getName)
                .toList();
    }
}
