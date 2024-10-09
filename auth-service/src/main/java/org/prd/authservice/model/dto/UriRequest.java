package org.prd.authservice.model.dto;

import jakarta.validation.constraints.NotNull;

public record UriRequest(
        @NotNull String uri,
        @NotNull String method
) {
}
