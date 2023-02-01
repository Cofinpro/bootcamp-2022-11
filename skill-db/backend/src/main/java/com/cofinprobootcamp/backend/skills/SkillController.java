package com.cofinprobootcamp.backend.skills;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/skills")
public class SkillController {
    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    /**
     * Endpoint to find all available skills from the database.
     *
     * @return A {@link List} of {@link String} representations of each {@link Skill}.
     */
    @GetMapping("")
    @PreAuthorize("hasAuthority(@authorityPrefix + 'SKILLS_GET_ALL')")
    public List<String> getSkills() {
        return skillService.getAllSkills();
    }
}
