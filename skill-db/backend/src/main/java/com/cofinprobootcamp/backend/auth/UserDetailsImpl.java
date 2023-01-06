package com.cofinprobootcamp.backend.auth;

import com.cofinprobootcamp.backend.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static com.cofinprobootcamp.backend.config.Constants.ROLE_PREFIX;

public class UserDetailsImpl implements UserDetails {

    private final String username;
    private final String password;
    private final String roleName;
    private final List<GrantedAuthority> authorities;
    private final boolean isLocked;
    private final String profileId;

    public UserDetailsImpl(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roleName = user.getRole().name();
        this.authorities = List.of(
                new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole().name())
        );
        this.isLocked = user.isLocked();
        this.profileId = user.getProfile() != null ? user.getProfile().getOuterId() : null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getProfileId() {
        return this.profileId;
    }
}
