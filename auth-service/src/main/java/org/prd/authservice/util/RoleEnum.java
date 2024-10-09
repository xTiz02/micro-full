package org.prd.authservice.util;

public enum RoleEnum {
    ROLE_ADMIN,
    ROLE_USER;


    public static RoleEnum getRole(String role) {
        if(role.equals("ROLE_ADMIN")) {
            return ROLE_ADMIN;
        } else if(role.equals("ROLE_USER")) {
            return ROLE_USER;
        }
        throw new IllegalArgumentException("Role not found");
    }
}
