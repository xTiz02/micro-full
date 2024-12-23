package org.prd.orderservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record OrderRequest(
        @NotNull(message = "User id is required")
        UUID userid,
        @NotNull(message = "Items is required") @NotEmpty(message = "Items is required")
        Set<String> items
) {
}