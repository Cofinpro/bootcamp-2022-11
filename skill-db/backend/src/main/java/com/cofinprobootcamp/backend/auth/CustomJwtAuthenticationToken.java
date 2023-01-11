package com.cofinprobootcamp.backend.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;

public class CustomJwtAuthenticationToken extends JwtAuthenticationToken {

    private final static String template = "%s [Principal=%s, Authenticated=%b, Details=%s, Role=%s, Granted Authorities=%s]";
    private final String role;
    private final boolean locked;
    private final String outerId;
    private final String profileId;

    public CustomJwtAuthenticationToken(Jwt jwt, Collection<? extends GrantedAuthority> authorities, String name, String role, boolean locked, String outerId, String profileId) {
        super(jwt, authorities, name);
        this.role = role;
        this.locked = locked;
        this.outerId = outerId;
        this.profileId = profileId;
    }

    public String getRoleName() {
        return this.role;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public String getProfileId() {
        return this.profileId;
    }

    public String getOuterId() {
        return this.outerId;
    }

    @Override
    public String toString() {
        return String.format(template,
                this.getClass().getName(),
                this.getName(),
                this.isAuthenticated(),
                this.getDetails().toString(),
                this.getRoleName(),
                this.getAuthorities().toString()
        );
    }
}
