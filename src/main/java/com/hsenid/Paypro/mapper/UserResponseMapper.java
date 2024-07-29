package com.hsenid.Paypro.mapper;

import com.hsenid.Paypro.dto.UserResponse;
import com.hsenid.Paypro.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserResponseMapper {

    public UserResponse toUserResponse(int statusCode, String message, String token, String refreshToken, String expirationTime, User user) {
        return new UserResponse(statusCode, null, message, token, refreshToken, expirationTime, user);
    }

    public UserResponse toUserResponseWithError(int statusCode, String error) {
        return new UserResponse(statusCode, error, null, null, null, null, null);
    }
}
