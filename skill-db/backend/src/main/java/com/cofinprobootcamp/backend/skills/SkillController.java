package com.cofinprobootcamp.backend.skills;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/skills")
public class SkillController {
    SkillRepository skillRepository;
    public SkillController(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }
    @PostMapping("")
    public Skill addSkill(@RequestBody Skill skill) {
        return skillRepository.save(skill);
    }
}
