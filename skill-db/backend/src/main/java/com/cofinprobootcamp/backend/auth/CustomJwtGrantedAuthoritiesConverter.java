package com.cofinprobootcamp.backend.auth;

import com.cofinprobootcamp.backend.config.Constants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Extracts the {@link SimpleGrantedAuthority}s from scope attributes found in our custom
 * {@link Jwt}. This class is inspired heavily by the {@link JwtGrantedAuthoritiesConverter}
 * standard implementation.
 *
 * @author l-rehm
 * @since 1.0
 */

public class CustomJwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<SimpleGrantedAuthority>> {

    /**
     * Extract {@link SimpleGrantedAuthority}s from the given {@link Jwt}.
     *
     * @param jwt The {@link Jwt} token
     * @return The {@link SimpleGrantedAuthority authorities} read from the token scopes
     */
    @Override
    public @NotNull Collection<SimpleGrantedAuthority> convert(Jwt jwt) {
        Collection<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String authority : getAuthorities(jwt)) {
            // Remove the outer JWT role prefix
            String authoritySubstring = authority.replaceFirst(Constants.JWT_ROLE_PREFIX, "");
            grantedAuthorities.add(
                    new SimpleGrantedAuthority(authoritySubstring)
            );
        }
        return grantedAuthorities;
    }

    private String getAuthoritiesClaimName() {
        return Constants.JWT_CLAIM_SCP;
    }

    private Collection<String> getAuthorities(Jwt jwt) {
        String claimName = getAuthoritiesClaimName();
        if (claimName == null) {
            return Collections.emptyList();
        }
        Object authorities = jwt.getClaim(claimName);
        if (authorities instanceof String) {
            if (StringUtils.hasText((String) authorities)) {
                return Arrays.asList(((String) authorities).split(" "));
            }
            return Collections.emptyList();
        }
        if (authorities instanceof Collection) {
            return castAuthoritiesToCollection(authorities);
        }
        return Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    private Collection<String> castAuthoritiesToCollection(Object authorities) {
        return (Collection<String>) authorities;
    }
}
