package org.prd.authservice.model.dto;

import java.util.UUID;

public record UserDto(
        UUID id,
        String username,
        String email,
        String password,
        String role,
        boolean account_expired,
        boolean account_locked,
        boolean credentials_expired,
        boolean enabled
) {
}
