package com.cofinprobootcamp.backend.skills;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class SkillServiceTest {
    @Mock
    SkillRepository skillRepository;

    SkillService skillService;
    @BeforeEach
    public void initialize() {
        MockitoAnnotations.openMocks(this);
        this.skillService = new SkillService(skillRepository);
    }


    @Test
    void given_existing_skill_name_when_saving_skill_then_return_existing_object() {
        Skill driving = new Skill("Fahren");
        Mockito.when(skillRepository.findSkillByName("Fahren")).thenReturn(Optional.of(driving));
        assertThat(skillService.findSkillIfExistsElseCreateSkill(List.of(driving.getName())))
                .isEqualTo(Set.of(driving));
    }
    @Test
    void given_nonexistent_skill_name_when_saving_skill_then_return_new_object() {
        Skill nonExistent = new Skill("NonExistent");
        Mockito.when(skillRepository.findSkillByName(nonExistent.getName())).thenReturn(Optional.empty());
        ArgumentCaptor<Skill> argumentCaptor= ArgumentCaptor.forClass(Skill.class);
        skillService.findSkillIfExistsElseCreateSkill(List.of(nonExistent.getName()));
        Mockito.verify(skillRepository,Mockito.times(1)).saveAndFlush(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getName()).isEqualTo(nonExistent.getName());
    }
}
