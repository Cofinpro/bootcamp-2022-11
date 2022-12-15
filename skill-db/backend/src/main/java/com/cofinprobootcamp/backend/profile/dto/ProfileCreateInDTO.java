package com.cofinprobootcamp.backend.profile.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ProfileCreateInDTO(
        @NotNull String jobTitle,
        String degree,
        @NotNull String primaryExpertise,
        String referenceText,
        List<String> skills,
        @NotNull String userEmail
) {
}
