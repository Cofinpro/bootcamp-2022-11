package com.cofinprobootcamp.backend.auth;

import com.cofinprobootcamp.backend.config.Constants;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.*;

/**
 * This class extends the standard {@link DaoAuthenticationProvider} implementation
 * to work with our custom {@link UserDetails} implementation (see {@link UserDetailsImpl}
 *
 * @author l-rehm
 * @version 1.0
 */
public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
                                                         UserDetails user) {
        UsernamePasswordAuthenticationToken auth =
                (UsernamePasswordAuthenticationToken)super.createSuccessAuthentication(principal, authentication, user);
        if (user instanceof UserDetailsImpl customUser) {
            String roleName = Constants.JWT_ROLE_PREFIX + customUser.getRoleName();
            auth = new CustomUsernamePasswordAuthenticationToken(auth, roleName);
        }
        return auth;
    }
}
