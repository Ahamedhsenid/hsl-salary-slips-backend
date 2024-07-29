package com.hsenid.Paypro.controller;

import com.hsenid.Paypro.dto.UserRequest;
import com.hsenid.Paypro.dto.UserResponse;
import com.hsenid.Paypro.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp( @RequestBody UserRequest signUpRequest) {
        try {
            UserResponse response = authService.signUp(signUpRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Adjust based on exception type
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<UserResponse> signIn( @RequestBody UserRequest signInRequest) {
        try {
            UserResponse response = authService.signIn(signInRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Adjust based on exception type
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<UserResponse> refreshToken( @RequestBody String refreshTokenRequest) {
        try {
            UserResponse response = authService.refreshToken(refreshTokenRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null); // Adjust based on exception type
        }
    }
}
