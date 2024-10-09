package org.prd.authservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.prd.authservice.util.RoleEnum;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @JoinColumn(nullable = false, unique = true)
    private RoleEnum roleEnum;
    private String description;

    @Override
    public String getAuthority() {
        return roleEnum != null ? roleEnum.name() : null;
    }
}
