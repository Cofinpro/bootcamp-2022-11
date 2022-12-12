package com.cofinprobootcamp.backend.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/token")
public class AuthController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity<Object> token(@RequestBody LoginRequest userLogin) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.username(), userLogin.password()));
        Map<String, String> tokens = tokenService.generateToken(authentication);
        return ResponseEntity.ok().body(Map.of("tokens", tokens, "username", userLogin.username()));

    }

    @GetMapping("/test")
    public String test() {
        return "hello world!";
    }

    @PostMapping("/refresh")
    public ResponseEntity<Object> refreshToken(@RequestBody RefreshTokenRequest refreshToken) {

        String token = refreshToken.getRefreshToken();

        if (tokenService.verifyToken(token, refreshToken.getUsername())) {
            String accessToken = tokenService.generateNewToken(refreshToken.getUsername());
            return ResponseEntity.ok().body(Map.of("accessToken", accessToken));
        }

        return new ResponseEntity<>("This user is not logged in anymore!", HttpStatus.UNAUTHORIZED);

    }

    @PostMapping("/verify")
    public boolean verifyToken(@RequestBody RefreshTokenRequest refreshToken) {
        return tokenService.verifyToken(refreshToken.getRefreshToken(), refreshToken.getUsername());
    }

}
