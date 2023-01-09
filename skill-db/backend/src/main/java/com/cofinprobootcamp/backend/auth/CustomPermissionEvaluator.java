package com.cofinprobootcamp.backend.auth;

import com.cofinprobootcamp.backend.config.Constants;
import com.cofinprobootcamp.backend.exceptions.InvalidAuthorityFormatException;
import com.cofinprobootcamp.backend.profile.dto.ProfileCreateInDTO;
import com.cofinprobootcamp.backend.user.dto.UserCreateInDTO;
import com.cofinprobootcamp.backend.role.UserPrivileges;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomPermissionEvaluator implements PermissionEvaluator {
    private static final String POSTFIX_SELF = "SELF";
    private static final String POSTFIX_ANY = "ANY";
    private static final String NO_POSTFIX = "";

    private static final String DELIMITER = "\\$";

    @Override
    public boolean hasPermission(
            Authentication auth, Object targetDomainObject, Object permission
    ) {
        System.out.println("Called hasPermission method 1 in CustomPermissionEvaluator");
        if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)) {
            return false;
        }
        System.out.println("passed first check");
        Set<String> matchingPermissions = matchPermissions(auth, permission.toString());
        if (matchingPermissions.isEmpty()) {
            return false;
        }
        System.out.println("passed second check");
        if (matchingPermissions.contains(NO_POSTFIX) || matchingPermissions.contains(POSTFIX_ANY)) {
            return true;
        }
        System.out.println("passed third check");
        if (matchingPermissions.contains(POSTFIX_SELF)) {
            return hasPermissionSelf(auth, targetDomainObject, buildPermissionString(permission, POSTFIX_SELF));
        }
        throw new InvalidAuthorityFormatException();
    }

    @Override
    public boolean hasPermission(
            Authentication auth, Serializable targetId, String targetType, Object permission
    ) {
        System.out.println("Called hasPermission method 2 in CustomPermissionEvaluator");
        if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
            return false;
        }
        Set<String> matchingPermissions = matchPermissions(auth, permission.toString());
        if (matchingPermissions.isEmpty()) {
            return false;
        }
        if (matchingPermissions.contains(NO_POSTFIX) || matchingPermissions.contains(POSTFIX_ANY)) {
            return true;
        }
        if (matchingPermissions.contains(POSTFIX_SELF)) {
            return hasPermissionSelf(auth, targetId, buildPermissionString(permission, POSTFIX_SELF));
        }
        throw new InvalidAuthorityFormatException();
    }

    /*
     * Internal method to provide single logic for both overwritten hasPermission() methods
     */
    private Set<String> matchPermissions(Authentication authentication, String permission) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(grantedAuthority -> grantedAuthority.startsWith(permission))
                .map(this::extractPostfix)
                .collect(Collectors.toSet());
    }

    /*
     * Internal method to provide single logic for both overwritten hasPermission() methods
     */
    private String extractPostfix(String grantedAuthority) {
        String[] authorityComponents = grantedAuthority.split(DELIMITER);
        if (authorityComponents.length == 1) {
            return NO_POSTFIX;
        }
        if (authorityComponents.length > 2) {
            return null;
        }
        return authorityComponents[authorityComponents.length - 1];
    }

    private String buildPermissionString(Object permission, String postfix) {
        StringBuilder builder = new StringBuilder(permission.toString());
        return builder
                .delete(0, Constants.AUTHORITY_PREFIX.length())
                .append(DELIMITER.charAt(DELIMITER.length() - 1))
                .append(postfix)
                .toString();
    }

    private boolean hasPermissionSelf(Authentication auth, Object targetDomainObject, String permission) {
        System.out.println("Permission: " + permission);
        System.out.println("AuthToken: " + auth.getClass().getSimpleName());
        if (auth instanceof CustomJwtAuthenticationToken customAuth) {
            switch (UserPrivileges.fromIdentifier(permission)) {
                case USERS_POST_NEW$SELF -> {
                    System.out.println("passed check 4");
                    if (targetDomainObject instanceof UserCreateInDTO inDTO) {
                        System.out.println("passed fifth check");
                        return mayCreateNewUserEntityForSelf(customAuth, inDTO);
                    }
                    printWarning("UserCreateInDTO", targetDomainObject.getClass().getSimpleName());
                }
                case USERS_DELETE_BY_ID$SELF -> {
                    if (targetDomainObject instanceof String id) {
                        return userIsSelf(customAuth, id);
                    }
                }
                case USERS_GET_BY_ID$SELF -> {
                    if (targetDomainObject instanceof String id) {
                        return userIsSelf(customAuth, id);
                    }
                }
                case PROFILES_POST_NEW$SELF -> {
                    if (targetDomainObject instanceof ProfileCreateInDTO inDTO) {
                        return mayCreateNewProfileForSelf(customAuth, inDTO);
                    }
                }
                case PROFILES_PATCH_BY_ID$SELF -> {
                    if (targetDomainObject instanceof String id) {
                        return userOwnsProfile(customAuth, id);
                    }
                }
                case PROFILES_DELETE_BY_ID$SELF -> {
                    if (targetDomainObject instanceof String id) {
                        return userOwnsProfile(customAuth, id);
                    }
                }
                case PROFILES_GET_BY_ID$SELF -> {
                    if (targetDomainObject instanceof String id) {
                        return userOwnsProfile(customAuth, id);
                    }
                }
                default -> {
                    return false;
                }
            }
        }
        return false; //TODO Implement
    }

    public void printWarning(String targetDomainObjectNameExpected, String targetDomainObjectNameActual) {
        System.out.printf("WARNING: Permission check failed, because wrong type of domain object was passed. Expected %s, found %s%n",
                targetDomainObjectNameExpected,
                targetDomainObjectNameActual);
    }

    // Custom methods for checking specific logic

    // Method 1 checks
    private boolean mayCreateNewUserEntityForSelf(CustomJwtAuthenticationToken authentication, UserCreateInDTO inDTO) {
        return authentication.getName().equals(inDTO.email());
    }

    private boolean mayCreateNewProfileForSelf(CustomJwtAuthenticationToken authentication, ProfileCreateInDTO inDTO) {
        return authentication.getName().equals(inDTO.email());
    }

    // Method 2 checks
    private boolean userIsSelf(CustomJwtAuthenticationToken authentication, String givenId) {
        return authentication.getOuterId().equals(givenId);
    }

    private boolean userOwnsProfile(CustomJwtAuthenticationToken authentication, String givenId) {
        return givenId.equals(authentication.getProfileId()); // "Yoda notation" for null check
    }
}
