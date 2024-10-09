package org.prd.authservice.util;

import lombok.extern.slf4j.Slf4j;
import org.prd.authservice.model.dto.UriRequest;
import org.prd.authservice.model.dto.UriRestrict;
import org.prd.authservice.service.impl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Slf4j
@Component
@ConfigurationProperties(prefix = "request-paths")
public class RouteValidator {

    @Autowired
    private JwtService jwtService;

    private List<UriRestrict> paths;

    public List<UriRestrict> getPaths() {
        return paths;
    }

    public void setPaths(List<UriRestrict> paths) {
        if(paths.stream().anyMatch(p->Objects.equals(p.uri(),null) || Objects.equals(p.methods(),null))){
            throw new IllegalArgumentException("Uri and methods cannot be null");
        }
        this.paths = paths;
    }

    public boolean isPathValid(String token,UriRequest dto) {
        String tokenRole = jwtService.extractRole(token);
        return paths.stream().filter(paths->paths.roles()!=null).anyMatch(p ->
                Pattern.matches(p.uri(), dto.uri()) &&
                        Arrays.asList(p.methods()).contains(dto.method()) &&
                        Arrays.asList(p.roles()).contains(tokenRole));
    }
}
