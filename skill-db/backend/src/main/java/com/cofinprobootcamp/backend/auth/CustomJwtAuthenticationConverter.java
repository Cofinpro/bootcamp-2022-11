package com.cofinprobootcamp.backend.auth;

import com.cofinprobootcamp.backend.exceptions.AuthTokenInfoOutOfSyncWithPersistenceException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.NoSuchElementException;

import static com.cofinprobootcamp.backend.config.Constants.JWT_CLAIM_OID;

/**
 * This is a custom implementation of the {@code org.springframework.core.convert.converter.Converter} interface.
 * It replaces the standard JWT implementation {@code JwtAuthenticationConverter} and is functionally similar.
 * <br>
 * One important difference is that before using the {@code convert(Jwt source)} method, a {@code UserDetailsService}
 * implementation must be set as strategy. This way access privileges are constructed from the database (i.e., from
 * the corresponding {@code User} entity), while the JWT payload is only needed for verification and to find the
 * correct {@code User} entity.
 *
 * @author l-rehm
 * @version 1.0
 */
public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private CustomJwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter;
    private UserDetailsServiceImpl userDetailsService;

    public CustomJwtAuthenticationConverter() {
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        assert jwtGrantedAuthoritiesConverter != null;
        assert userDetailsService != null;

        // Get payload from JWT
        String principalClaimName = source.getClaimAsString(JwtClaimNames.SUB);
        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(principalClaimName);

        // Verify payload with userDetails
        verifyTokenPayload(source, userDetails);

        // Construct CustomJwtAuthToken from userDetails
        return new CustomJwtAuthenticationToken(
                    source,
                    userDetails.getAuthorities(),
                    principalClaimName,
                    userDetails.getRoleName(),
                    !userDetails.isAccountNonLocked(),
                    userDetails.getOuterId(),
                    userDetails.getProfileId()
        );
    }

    public void setJwtGrantedAuthoritiesConverter(
            CustomJwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter) {
        Assert.notNull(jwtGrantedAuthoritiesConverter, "jwtGrantedAuthoritiesConverter cannot be null");
        this.jwtGrantedAuthoritiesConverter = jwtGrantedAuthoritiesConverter;
    }

    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    private String extractRoleFromJwtSource(Jwt source) {
        Collection<SimpleGrantedAuthority> authorities = jwtGrantedAuthoritiesConverter.convert(source);
        SimpleGrantedAuthority tokenRole;
        try {
            if (authorities.size() != 1) { // convert() method of Custom...Converter is guaranteed to be not null
                throw new NoSuchElementException();
            }
            tokenRole =  authorities.stream().findFirst().get();
        } catch (NoSuchElementException noSuchElementException) {
            // Internally a user must have EXACTLY one role (at least StandardRoles.UNDEFINED)
            throw new AuthTokenInfoOutOfSyncWithPersistenceException(
                    OAuth2ErrorCodes.INVALID_TOKEN,
                    "[Corrupted data!] Nutzer hat nicht genau eine Rolle!",
                    noSuchElementException
            );
        }
        return tokenRole.getAuthority();
    }

    private void verifyTokenPayload(Jwt source, UserDetailsImpl userDetails) {
        String tokenRole = extractRoleFromJwtSource(source);
        if (
                !userDetails.getOuterId().equals(source.getClaimAsString(JWT_CLAIM_OID))
                        || !userDetails.getRoleName().equals(tokenRole)) {
            throw new AuthTokenInfoOutOfSyncWithPersistenceException(
                    OAuth2ErrorCodes.INVALID_TOKEN,
                    "Login ist nicht (mehr) gültig. Grund ist vermutlich eine Änderung der Nutzerrolle. Bitte erneut einloggen."
            );
        }
    }
}
