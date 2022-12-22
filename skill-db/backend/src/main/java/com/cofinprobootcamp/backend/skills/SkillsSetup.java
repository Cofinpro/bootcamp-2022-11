package com.cofinprobootcamp.backend.skills;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@Order(2)
public class SkillsSetup {
    private final SkillService skillService;

    @Autowired
    public SkillsSetup(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostConstruct
    public void init() {
        initStandardSkills();
    }

    private void initStandardSkills() {
        List<String> standardSkills = List.of("Java",
                "Spring Boot",
                "JavaScript",
                "HTML",
                "CSS",
                "Scrum");
        skillService.findSkillIfExistsElseCreateSkill(standardSkills);
    }
}
