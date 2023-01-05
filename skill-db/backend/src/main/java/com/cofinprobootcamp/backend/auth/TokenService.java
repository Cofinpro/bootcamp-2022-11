package com.cofinprobootcamp.backend.auth;

import com.cofinprobootcamp.backend.user.User;
import com.cofinprobootcamp.backend.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.cofinprobootcamp.backend.config.ProfileConfiguration.*;

@Service
public class TokenService {

    private final JwtEncoder encoder;
    private final JwtDecoder jwtDecoder;
    private final UserRepository userRepository;

    public TokenService(JwtEncoder encoder, JwtDecoder jwtDecoder, UserRepository userRepository) {
        this.encoder = encoder;
        this.jwtDecoder = jwtDecoder;
        this.userRepository = userRepository;
    }

    /**
     * Generates the access- and refresh token for the given user
     * @param authentication
     * @return Map, consisting of access token and refresh token
     */
    public Map<String, String> generateToken(Authentication authentication) {
        String accessToken = generateNewAccessToken(authentication.getName());
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
                .issuer("Cofinpro Bootcamp")
                .issuedAt(now)
                .expiresAt(now.plus(REFRESH_TOKEN_DURATION_SECONDS, ChronoUnit.SECONDS))
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

        // getting the role of the user, because he is an anonymousUser right now
        // (when trying to access the userDetails with Authentication)
        String role = "ROLE_";
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()) {
            role += user.get().getRole().getName();
        }

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("Cofinpro Bootcamp")
                .issuedAt(now)
                .expiresAt(now.plus(ACCESS_TOKEN_DURATION_SECONDS, ChronoUnit.SECONDS))
                .subject(username)
                .claim("scope", role)
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
