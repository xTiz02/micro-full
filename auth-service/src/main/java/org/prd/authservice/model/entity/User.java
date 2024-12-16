package org.prd.authservice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @JoinColumn(nullable = false, unique = true)
    private String username;
    @JoinColumn(nullable = false, unique = true)
    private String email;
    @JoinColumn(nullable = false)
    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
    private boolean account_expired;
    private boolean account_locked;
    private boolean credentials_expired;
    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(role != null){
           return List.of(role);
        }
        return new ArrayList<>();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !account_expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !account_locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentials_expired;
    }

    public void init(){
        this.account_expired = false;
        this.account_locked = false;
        this.credentials_expired = false;
        this.enabled = true;
    }
}