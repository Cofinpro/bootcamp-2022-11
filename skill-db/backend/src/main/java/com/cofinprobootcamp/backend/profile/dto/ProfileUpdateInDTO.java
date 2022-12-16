package com.cofinprobootcamp.backend.profile.dto;

import com.cofinprobootcamp.backend.config.Regex;
import jakarta.validation.constraints.*;

import java.util.List;

public record ProfileUpdateInDTO(@NotBlank String firstName, @NotBlank String lastName, @NotBlank String email,
                                 @NotBlank String jobTitle, @NotBlank String degree, @NotNull String primaryExpertise,
                                 @NotBlank String referenceText, @NotEmpty List<String> skills,
                                 @NotBlank @Pattern(regexp = Regex.VALID_PHONE_NUMBER) String phoneNumber) {
}
