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

/**
 * This is a custom implementation of the {@link PermissionEvaluator} interface
 * that supports {@code @PreAuthorized} annotations that take our custom role management
 * into account.
 * <br>
 * As per the interface definition the class provides two different implementations
 * of the {@code hasPermission()} method to use in a SpEL query.
 *
 * @author l-rehm
 * @version 1.0
 */
public class CustomPermissionEvaluator implements PermissionEvaluator {
    private static final String POSTFIX_SELF = "SELF";
    private static final String POSTFIX_ANY = "ANY";
    private static final String NO_POSTFIX = "";

    private static final String DELIMITER = "\\$";

    @Override
    public boolean hasPermission(
            Authentication auth, Object targetDomainObject, Object permission
    ) {
        if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)) {
            return false;
        }
        return p(auth, targetDomainObject, permission.toString());
    }

    @Override
    public boolean hasPermission(
            Authentication auth, Serializable targetId, String targetType, Object permission
    ) {
        if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
            return false;
        }
        return p(auth, targetId, permission.toString());
    }

    /*
     * Internal method to provide single logic for both overwritten hasPermission() methods
     */
    private boolean p(Authentication auth, Object targetDomainObject, String permission) {
        Set<String> matchingPermissions = matchPermissions(auth, permission);
        if (matchingPermissions.isEmpty()) {
            return false;
        }
        if (matchingPermissions.contains(NO_POSTFIX) || matchingPermissions.contains(POSTFIX_ANY)) {
            return true;
        }
        if (matchingPermissions.contains(POSTFIX_SELF)) {
            return hasPermissionSelf(auth, targetDomainObject, buildPermissionString(permission, POSTFIX_SELF));
        }
        throw new InvalidAuthorityFormatException();
    }

    /*
     * Internal method to provide single logic for both overwritten hasPermission() methods
     */
    private Set<String> matchPermissions(Authentication authentication, String permission) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(grantedAuthority -> extractPrefix(grantedAuthority).equals(permission))
                .map(this::extractPostfix)
                .collect(Collectors.toSet());
    }

    private String extractPrefix(String grantedAuthority) {
        return grantedAuthority.split(DELIMITER)[0];
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
        if (auth instanceof CustomJwtAuthenticationToken customAuth) {
            return switch (UserPrivileges.fromIdentifier(permission)) {
                case USERS_POST_NEW$SELF -> {
                    if (targetDomainObject instanceof UserCreateInDTO inDTO) {
                        yield mayCreateNewUserEntityForSelf(customAuth, inDTO);
                    }
                    printWarning("UserCreateInDTO", targetDomainObject.getClass().getSimpleName());
                    yield false;
                }
                case USERS_DELETE_BY_ID$SELF, USERS_BY_ID_GET_PROFILE$SELF, USERS_BY_ID_GET_PROFILE_EXISTS$SELF -> {
                    if (targetDomainObject instanceof String id) {
                        yield userIsSelf(customAuth, id);
                    }
                    yield false;
                }
                case PROFILES_POST_NEW$SELF -> {
                    if (targetDomainObject instanceof ProfileCreateInDTO inDTO) {
                        yield mayCreateNewProfileForSelf(customAuth, inDTO);
                    }
                    printWarning("ProfileCreateInDTO", targetDomainObject.getClass().getSimpleName());
                    yield false;
                }
                case PROFILES_PATCH_BY_ID$SELF, PROFILES_DELETE_BY_ID$SELF, PROFILES_GET_BY_ID$SELF -> {
                    if (targetDomainObject instanceof String id) {
                        yield userOwnsProfile(customAuth, id);
                    }
                    yield false;
                }
                case ROLES_GET_BY_ID$SELF -> {
                    if (targetDomainObject instanceof String id) {
                        yield userHasRole(customAuth, id);
                    }
                    yield false;
                }
                default -> false;
            };
        }
        return false;
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

    private boolean userHasRole(CustomJwtAuthenticationToken authentication, String givenIdentifier) {
        return givenIdentifier.equals(authentication.getRoleName()); // "Yoda notation" for null check
    }
}
