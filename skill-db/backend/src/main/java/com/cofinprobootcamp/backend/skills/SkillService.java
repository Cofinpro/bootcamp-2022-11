package com.cofinprobootcamp.backend.skills;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class SkillService {
    SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }
    public Set<Skill> findSkillIfExistsElseCreateSkill(List<String> skillInputs) {
        return skillInputs.stream()
                .map(
                        name -> skillRepository.findSkillByName(name)
                                .orElse(skillRepository.save(new Skill(name))))
                .collect(Collectors.toSet());
    }
}
