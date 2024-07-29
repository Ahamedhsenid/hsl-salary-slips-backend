package com.hsenid.Paypro.mapper;

import com.hsenid.Paypro.dto.UserRequest;
import com.hsenid.Paypro.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserRequestMapper {

    private final PasswordEncoder passwordEncoder;

    public UserRequestMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User toUser(UserRequest userRequest) {
        User user = new User();
        user.setEmail(userRequest.email());
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setRole(userRequest.role());
        return user;
    }
}
