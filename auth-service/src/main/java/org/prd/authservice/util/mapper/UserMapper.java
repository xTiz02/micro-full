package org.prd.authservice.util.mapper;

import org.prd.authservice.model.dto.RegisterDto;
import org.prd.authservice.model.dto.UserDto;
import org.prd.authservice.model.entity.User;

public class UserMapper {

    public static User registerDtoToUser(RegisterDto registerDto) {
        User user = new User();
        user.setUsername(registerDto.username());
        user.setEmail(registerDto.email());
        user.init();
        return user;
    }

    public static UserDto userToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                null,
                user.getRole().getAuthority(),
                user.isAccount_expired(),
                user.isAccount_locked(),
                user.isCredentialsNonExpired(),
                user.isEnabled()
        );
    }
}
