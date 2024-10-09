package org.prd.authservice.model.dto;

public record UriRestrict(
        String uri,
        String[] methods,
        String[] roles
) {
}
