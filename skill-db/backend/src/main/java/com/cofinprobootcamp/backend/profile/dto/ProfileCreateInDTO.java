package com.cofinprobootcamp.backend.profile.dto;

import com.cofinprobootcamp.backend.config.Regex;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record ProfileCreateInDTO(@NotNull Long outerId, @NotBlank String firstName, @NotBlank String lastName,
                                 @NotBlank String jobTitle, @NotBlank String degree, @NotNull String primaryExpertise,
                                 @NotBlank String referenceText, @NotEmpty List<String> skills,
                                 @NotBlank @Pattern(regexp = Regex.VALID_PHONE_NUMBER) String phoneNumber,
                                 @NotBlank @Email @Pattern(regexp = Regex.VALID_MAIL_ADDRESS) String email,
                                 @NotNull @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthDate) {
}
