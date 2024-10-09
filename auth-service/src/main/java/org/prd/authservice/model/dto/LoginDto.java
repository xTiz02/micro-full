package org.prd.authservice.model.dto;

import jakarta.validation.constraints.NotNull;

public record LoginDto(
    @NotNull String username,
    @NotNull String password
) {
}
