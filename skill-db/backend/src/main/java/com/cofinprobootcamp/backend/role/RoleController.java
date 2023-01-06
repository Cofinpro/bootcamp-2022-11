package com.cofinprobootcamp.backend.role;

import com.cofinprobootcamp.backend.config.Constants;
import com.cofinprobootcamp.backend.role.dto.RoleDetailsOutDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Roles endpoint.
 *
 * @version 2.0
 */
@Component(value = "roleController")
@RestController
@RequestMapping(path = "/api/v1/" + RoleController.LOCAL_CONTROLLER_PREFIX + "s")
public class RoleController {
    public static final String LOCAL_CONTROLLER_PREFIX = "role";
    public static final String LOCAL_PERMISSION_PREFIX = "privilege";
    public String localPermissionPrefix() {
        //return Constants.AUTHORITY_PREFIX + LOCAL_CONTROLLER_PREFIX;
        return "role";
    }

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Endpoint to search for a specific role by its identifier.
     *
     * @param identifier The unique short name of the role for internal identification
     * @return A {@code RoleDetailsOutDTO} containing any relevant information about the role, if it exists
     */
    @GetMapping(path = "{identifier}")
    @PreAuthorize("hasPermission(#identifier, @roleController.localPermissionPrefix(), 'user')")
    public RoleDetailsOutDTO getRoleById(@PathVariable String identifier) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        return roleService.getRoleByIdentifier(identifier);
    }

    /**
     * Endpoint to find all available roles.
     *
     * @return A {@code RoleOverviewOutDTO} containing any relevant information about all roles
     */
    @GetMapping(path = "")
    @PreAuthorize("hasAnyAuthority(@jwtGrantedAuthoritiesPrefix + 'ADMIN', @jwtGrantedAuthoritiesPrefix + 'HR')")
    public List<RoleDetailsOutDTO> getAllRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        return roleService.getAllRoles();
    }

}
