package com.cofinprobootcamp.backend.skills;

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
    public List<String> getSkills() {
        return skillRepository
                .findAll()
                .stream()
                .map(Skill::getName)
                .toList();
    }
}
