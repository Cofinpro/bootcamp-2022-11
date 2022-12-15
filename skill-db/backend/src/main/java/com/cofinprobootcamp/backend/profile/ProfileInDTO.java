package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.enums.Expertises;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record ProfileInDTO(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotBlank
        String jobTitle,
        @NotBlank
        String degree,
        @NotNull
        Expertises primaryExpertise,
        @NotBlank
        String referenceText,
        @NotEmpty
        List<String> skills,
        @NotBlank
        @Pattern(regexp = "\\d{11,13}")
        String phoneNumber,
        @NotBlank
        @Email
        @Pattern(regexp = "[\\w.]+@\\w+\\.\\w+")
        String email,
        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate birthDate
) {
}
