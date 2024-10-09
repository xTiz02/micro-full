package org.prd.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurity {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(c->c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authConfig->{
                    /*authConfig.requestMatchers("/user/restrict/**","/auth/restrict/**").hasAnyAuthority(RoleEnum.ROLE_USER.name(),RoleEnum.ROLE_ADMIN.name());
                    authConfig.requestMatchers("/auth/open/**").permitAll();*/
                    authConfig.anyRequest().permitAll();
                })
                //.authenticationProvider(authenticationProvider())//esto es para que se pueda autenticar con el provider que creamos
                .build();
    }
}
