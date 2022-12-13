package com.cofinprobootcamp.backend.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TokenService {

    private final JwtEncoder encoder;

    private final JwtDecoder jwtDecoder;

    public TokenService(JwtEncoder encoder, JwtDecoder jwtDecoder) {
        this.encoder = encoder;
        this.jwtDecoder = jwtDecoder;
    }

    /**
     * Generates the access- and refresh token for the given user
     * @param authentication
     * @return Map, consisting of access token and refresh token
     */
    public Map<String, String> generateToken(Authentication authentication) {
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("Cofinpro Bootcamp")
                .issuedAt(now)
                .expiresAt(now.plus(2, ChronoUnit.MINUTES))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        String accessToken = encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        String refreshToken = generateRefreshToken(authentication);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);

        return tokens;
    }

    /**
     * Generates a new refresh token for the given user
     * @param authentication
     * @return String with the refresh token
     */
    public String generateRefreshToken(Authentication authentication) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(now.plus(3, ChronoUnit.SECONDS))
                .subject(authentication.getName())
                .build();
        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    /**
     * Generates a new access token for the given user
     * @param username
     * @return String with the access token
     */
    public String generateNewAccessToken(String username) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(now.plus(2, ChronoUnit.MINUTES))
                .subject(username)
                .build();
        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    /**
     * Verifies if the given token is not expired
     * @param token
     * @param username
     * @return boolean: true in case the token is not expired; false in case the token is expired
     */
    public boolean verifyToken(String token, String username) {
        try {
            Jwt jwt = jwtDecoder.decode(token);

            if (jwt.getExpiresAt().isBefore(Instant.now())) {
                return false;
            }

            return jwt.getSubject().equals(username);
        } catch (Exception exception) {
            return false;
        }
    }

}
