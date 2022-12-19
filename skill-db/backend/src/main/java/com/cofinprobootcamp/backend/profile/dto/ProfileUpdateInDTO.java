package com.cofinprobootcamp.backend.profile.dto;

import com.cofinprobootcamp.backend.config.Regex;
import javax.validation.constraints.*;

import java.util.List;

public record ProfileUpdateInDTO(@NotBlank String firstName, @NotBlank String lastName,
                                 @NotBlank String jobTitle, @NotBlank String degree, @NotNull String primaryExpertise,
                                 @NotNull String referenceText, @NotEmpty List<@NotBlank String> skills,
                                 @NotBlank @Pattern(regexp = Regex.VALID_PHONE_NUMBER) String phoneNumber) {
}
