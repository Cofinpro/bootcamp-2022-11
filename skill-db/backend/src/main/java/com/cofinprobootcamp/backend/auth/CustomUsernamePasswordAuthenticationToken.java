package com.cofinprobootcamp.backend.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * This class extends the standard {@link UsernamePasswordAuthenticationToken} class,
 * s.t. it works with our custom role management system.
 *
 * @author l-rehm
 * @version 1.0
 */
public class CustomUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final static String template = "%s [Principal=%s, Authenticated=%b, Details=%s, Role=%s, Granted Authorities=%s]";
    private String outerId;
    private String roleName = "";

    public CustomUsernamePasswordAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    /**
     * This constructor should only be used by <code>AuthenticationManager</code> or
     * <code>AuthenticationProvider</code> implementations that are satisfied with
     * producing a trusted (i.e. {@link #isAuthenticated()} = <code>true</code>)
     * authentication token.
     */
    public CustomUsernamePasswordAuthenticationToken(UsernamePasswordAuthenticationToken parentToken, String outerId, String roleName) {
        super(parentToken.getPrincipal(), parentToken.getCredentials(), parentToken.getAuthorities());
        this.outerId = outerId;
        this.roleName = roleName;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roleName));
    }

    public String getOuterId() {
        return outerId;
    }

    @Override
    public String toString() {
        return String.format(template,
                this.getClass().getSimpleName(),
                this.getName(),
                this.isAuthenticated(),
                this.getDetails() != null ? this.getDetails().toString() : "[PROTECTED]",
                this.roleName,
                super.getAuthorities().toString()
        );
    }
}
