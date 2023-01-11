package com.cofinprobootcamp.backend.role;

import com.cofinprobootcamp.backend.role.dto.RoleDetailsOutDTO;
import com.cofinprobootcamp.backend.role.dto.RoleOverviewOutDTO;
import com.cofinprobootcamp.backend.user.dto.UserOutDTO;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasPermission(#identifier, 'RoleDetailsOutDTO', @authorityPrefix + 'ROLES_GET_BY_ID')")
    public RoleDetailsOutDTO getRoleById(@PathVariable String identifier) {
        return roleService.getRoleByIdentifier(identifier);
    }

    /**
     * Endpoint to find all available roles.
     *
     * @return A {@code RoleOverviewOutDTO} containing any relevant information about all roles
     */
    @GetMapping(path = "")
    @PreAuthorize("hasAuthority(@authorityPrefix + 'ROLES_GET_ALL')")
    public List<RoleOverviewOutDTO> getAllRoles() {
        return roleService.getAllRoles();
    }

    /**
     * Endpoint to find all users with a specific role by its identifier.
     *
     * @param identifier The unique short name of the role for internal identification
     * @return A list of usernames of all users with this role
     */
    @GetMapping(path = "{identifier}/users")
    @PreAuthorize("hasAuthority(@authorityPrefix + 'ROLES_BY_ID_GET_USERS_ALL')")
    public List<UserOutDTO> getAllUsersForRoleById(@PathVariable String identifier) {
        return roleService.getUsersByRole(identifier);
    }

}
