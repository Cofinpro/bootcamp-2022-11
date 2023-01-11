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
    SkillService skillService;

    @BeforeEach
    public void initialize() {
        MockitoAnnotations.openMocks(this);
        skillController = new SkillController(skillService);
    }
    @Test
    void given_repository_of_skills_then_return_list_of_skill_names() {
        List<String> skillList = List.of(
                "skill1",
                "skill2"
        );
        Mockito.when(skillService.getAllSkills()).thenReturn(skillList);
        List<String> nameList = List.of(
                skillList.get(0),
                skillList.get(1)
        );
        assertThat(skillController.getSkills()).isEqualTo(nameList);
    }
}
