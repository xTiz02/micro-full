package org.prd.authservice.model.dto;

import jakarta.validation.constraints.NotNull;

public record RegisterDto(
        @NotNull String username,
        @NotNull String password,
        @NotNull String email
) {
}
