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
                        name -> {
                            if (skillRepository.findSkillByName(name.trim()).isPresent()) {
                                return skillRepository.findSkillByName(name.trim()).get();
                            } else{
                                return skillRepository.saveAndFlush(new Skill(name.trim()));
                            }}
                )
                .collect(Collectors.toSet());
    }

    public List<String> getAllSkills() {
        return skillRepository
                .findAll()
                .stream()
                .map(Skill::getName)
                .toList();
    }
}
