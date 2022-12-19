package com.cofinprobootcamp.backend.skills;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
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
    void findSkillIfExists() {
        Skill driving = new Skill("Fahren");
        Mockito.when(skillRepository.findSkillByName("Fahren")).thenReturn(Optional.of(driving));
        assertThat(skillService.findSkillIfExistsElseCreateSkill(List.of(driving.getName())))
                .isEqualTo(Set.of(driving));
    }
    @Test
    void elseCreateSkill() {
        Skill nonExistent = new Skill("NonExistent");
        Mockito.when(skillRepository.findSkillByName(nonExistent.getName())).thenReturn(Optional.empty());
        ArgumentCaptor<Skill> argumentCaptor= ArgumentCaptor.forClass(Skill.class);
        skillService.findSkillIfExistsElseCreateSkill(List.of(nonExistent.getName()));
        Mockito.verify(skillRepository,Mockito.times(1)).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getName()).isEqualTo(nonExistent.getName());
    }
}
