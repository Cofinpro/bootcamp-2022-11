package com.cofinprobootcamp.backend.role;

import com.cofinprobootcamp.backend.role.dto.RoleDetailsOutDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Roles endpoint.
 *
 * @version 2.0
 */
@RestController
@RequestMapping(path = "/api/v1/roles")
public class RoleController {
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
