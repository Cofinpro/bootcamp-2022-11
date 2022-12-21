package com.cofinprobootcamp.backend.role.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

public record RoleCreateInDTO(@NotBlank String shortName, @NotBlank String descriptiveName,
                              @NotNull Map<String, Map<String, String>> userRights) {
}