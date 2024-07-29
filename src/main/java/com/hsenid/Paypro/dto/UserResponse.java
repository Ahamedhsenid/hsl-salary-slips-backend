package com.hsenid.Paypro.dto;

import com.hsenid.Paypro.model.User;

public record UserResponse(int statusCode, String error, String message, String token, String refreshToken, String expirationTime, User user) { }
