package com.cofinprobootcamp.backend.user.dto;

import com.cofinprobootcamp.backend.enums.RolesEnum;

import javax.validation.constraints.NotBlank;

public record UserCreateInDTO(@NotBlank String email, RolesEnum userRole) {
}
