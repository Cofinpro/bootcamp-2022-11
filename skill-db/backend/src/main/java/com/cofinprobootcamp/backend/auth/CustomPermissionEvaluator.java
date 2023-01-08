package com.cofinprobootcamp.backend.auth;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

public class CustomPermissionEvaluator implements PermissionEvaluator {
    private static final String SELF = "SELF";
    private static final String ANY = "ANY";

    private static final String DELIMITER = "\\$";

    @Override
    public boolean hasPermission(
            Authentication auth, Object targetDomainObject, Object permission
    ) {
        System.out.println("Called method 1");
        if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)) {
            return false;
        }
        if (hasPermissionAny(auth, permission.toString())) {
            return true;
        }
        if (hasPermissionSelf(auth, targetDomainObject, permission.toString())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasPermission(
            Authentication auth, Serializable targetId, String targetType, Object permission
    ) {
        System.out.println("Called method 2");
        if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
            return false;
        }
        if (hasPermissionAny(auth, permission.toString())) {
            return true;
        }
        if (hasPermissionSelf(auth, targetId, targetType, permission.toString())) {
            return true;
        }
        return false;
    }

    /*
     * Internal method to provide single logic for both overwritten hasPermission() methods
     */
    private boolean hasPermissionAny(Authentication auth, String permission) {
        for (GrantedAuthority authority : auth.getAuthorities()) {
            String[] authorityComponents = authority.getAuthority().split(DELIMITER);
            if (authorityComponents.length == 2) {
                String prefix = authorityComponents[0];
                String postfix = authorityComponents[1];
                if (ANY.equals(postfix) && prefix.startsWith(permission)) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * Internal method to provide single logic for both overwritten hasPermission() methods
     */
    private boolean hasPermissionSelf(Authentication auth, Object targetDomainObject, String permission) {
        return false; //TODO Implement
    }

    /*
     * Internal method to provide single logic for both overwritten hasPermission() methods
     */
    private boolean hasPermissionSelf(Authentication auth, Serializable targetId, String targetType, String permission) {
        return false; //TODO Implement
    }
}
