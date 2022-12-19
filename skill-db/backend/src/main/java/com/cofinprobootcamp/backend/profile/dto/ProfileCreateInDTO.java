package com.cofinprobootcamp.backend.profile.dto;

import com.cofinprobootcamp.backend.config.Regex;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record ProfileCreateInDTO(@NotBlank @Pattern(regexp = Regex.VALID_MAIL_ADDRESS) String email,
                                 @NotBlank String firstName, @NotBlank String lastName, @NotBlank String jobTitle,
                                 @NotBlank String degree, @NotNull String primaryExpertise,
                                 @NotBlank String referenceText, @NotEmpty List<@NotBlank String> skills,
                                 @NotBlank @Pattern(regexp = Regex.VALID_PHONE_NUMBER) String phoneNumber,
                                 @NotNull @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthDate) {
}
