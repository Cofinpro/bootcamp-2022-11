package com.cofinprobootcamp.backend.auth;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.Assert;

import java.util.Collection;

public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter;
    private UserDetailsService userDetailsService;

    public CustomJwtAuthenticationConverter() {
    }

    public CustomJwtAuthenticationConverter(Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter) {
        this.jwtGrantedAuthoritiesConverter = jwtGrantedAuthoritiesConverter;
    }

    public CustomJwtAuthenticationConverter(
            Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter,
            UserDetailsService userDetailsService) {
        this(jwtGrantedAuthoritiesConverter);
        this.userDetailsService = userDetailsService;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        Collection<GrantedAuthority> authorities = jwtGrantedAuthoritiesConverter.convert(source);
        String principalClaimName = source.getClaimAsString(JwtClaimNames.SUB);
        UserDetails userDetails = userDetailsService.loadUserByUsername(principalClaimName);
        if (userDetails instanceof UserDetailsImpl customUserDetails) {
            return new CustomJwtAuthenticationToken(
                    source,
                    authorities,
                    principalClaimName,
                    customUserDetails.getRoleName(),
                    !customUserDetails.isAccountNonLocked(),
                    customUserDetails.getProfileId()
            );
        } else {
            return new JwtAuthenticationToken(source, authorities, principalClaimName);
        }
    }

    public void setJwtGrantedAuthoritiesConverter(
            Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter) {
        Assert.notNull(jwtGrantedAuthoritiesConverter, "jwtGrantedAuthoritiesConverter cannot be null");
        this.jwtGrantedAuthoritiesConverter = jwtGrantedAuthoritiesConverter;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
