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

    /**
     * Handles the login. In case the user credentials are valid, it returns an object containing
     * the username of the user, who tries to log in; an access token and refresh token
     * @param userLogin
     * @return username, access token and refresh token
     */
    @PostMapping
    public ResponseEntity<Object> token(@RequestBody LoginRequest userLogin) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.username(), userLogin.password()));
        Map<String, String> tokens = tokenService.generateToken(authentication);
        return ResponseEntity.ok().body(Map.of("tokens", tokens, "username", userLogin.username()));

    }

    /**
     * route for testing the authentication
     * @return test-string
     */
    @GetMapping("/test")
    public String test() {
        return "hello world!";
    }

    /**
     * Delivers a new access token, in case the refresh token is not expired
     * @param refreshToken
     * @return access token
     */
    @PostMapping("/refresh")
    public ResponseEntity<Object> refreshToken(@RequestBody RefreshTokenRequest refreshToken) {

        String token = refreshToken.getRefreshToken();

        if (tokenService.verifyToken(token, refreshToken.getUsername())) {
            String accessToken = tokenService.generateNewAccessToken(refreshToken.getUsername());
            return ResponseEntity.ok().body(Map.of("accessToken", accessToken));
        }

        return new ResponseEntity<>("This user is not logged in anymore!", HttpStatus.UNAUTHORIZED);

    }

    /**
     * Verifies if the refresh token is expired
     * @param refreshToken
     * @return true, in case the token is not expired. Otherwise false
     */
    @PostMapping("/verify")
    public boolean verifyToken(@RequestBody RefreshTokenRequest refreshToken) {
        return tokenService.verifyToken(refreshToken.getRefreshToken(), refreshToken.getUsername());
    }

}
