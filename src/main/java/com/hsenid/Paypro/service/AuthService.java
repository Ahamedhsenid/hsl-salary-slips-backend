package com.hsenid.Paypro.service;

import com.hsenid.Paypro.dto.UserRequest;
import com.hsenid.Paypro.dto.UserResponse;
import com.hsenid.Paypro.mapper.UserRequestMapper;
import com.hsenid.Paypro.mapper.UserResponseMapper;
import com.hsenid.Paypro.model.User;
import com.hsenid.Paypro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRequestMapper userRequestMapper;

    @Autowired
    private UserResponseMapper userResponseMapper;

    public UserResponse signUp(UserRequest userRequest) {
        if (userRepository.findByEmail(userRequest.email()).isPresent()) {
            throw new RuntimeException("Email is already in use");
        }

        try {
            User user = userRequestMapper.toUser(userRequest);
            User savedUser = userRepository.save(user);

            if (savedUser != null && savedUser.getId() != null) {
                return userResponseMapper.toUserResponse(200, "User has been registered successfully", null, null, null, savedUser);
            } else {
                throw new RuntimeException("Failed to register user");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during registration: " + e.getMessage(), e);
        }
    }

    public UserResponse signIn(UserRequest userRequest) {
        Optional<User> userOptional = userRepository.findByEmail(userRequest.email());
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Invalid email");
        }

        try {
            User user = userOptional.get();

            if (!authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.email(), userRequest.password())).isAuthenticated()) {
                throw new RuntimeException("Invalid password");
            }

            String jwt = jwtUtils.generateToken(user);
            String refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

            return userResponseMapper.toUserResponse(200, "User has been logged in successfully", jwt, refreshToken, "24Hr", user);
        } catch (Exception e) {
            throw new RuntimeException("Error during login: " + e.getMessage(), e);
        }
    }

    public UserResponse refreshToken(String refreshToken) {
        try {
            String email = jwtUtils.extractUsername(refreshToken);
            User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

            if (jwtUtils.isTokenValid(refreshToken, user)) {
                String newJwt = jwtUtils.generateToken(user);
                return userResponseMapper.toUserResponse(200, "Token has been refreshed successfully", newJwt, refreshToken, "24Hr", user);
            } else {
                throw new RuntimeException("Invalid refresh token");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during token refresh: " + e.getMessage(), e);
        }
    }
}
