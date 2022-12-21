package com.cofinprobootcamp.backend.skills;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
@ExtendWith(SpringExtension.class)
class SkillControllerTest {
    SkillController skillController;
    @Mock
    SkillRepository skillRepository;

    @BeforeEach
    public void initialize() {
        MockitoAnnotations.openMocks(this);
        skillController = new SkillController(skillRepository);
    }
    @Test
    void given_repository_of_skills_then_return_list_of_skill_names() {
        List<Skill> skillList = List.of(
                new Skill("name"),
                new Skill("name2")
        );
        Mockito.when(skillRepository.findAll()).thenReturn(skillList);
        List<String> nameList = List.of(
                skillList.get(0).getName(),
                skillList.get(1).getName()
        );
        assertThat(skillController.getSkills()).isEqualTo(nameList);
    }
}
