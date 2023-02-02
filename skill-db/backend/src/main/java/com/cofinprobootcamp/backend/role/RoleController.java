package com.cofinprobootcamp.backend.role;

import com.cofinprobootcamp.backend.role.dto.RoleOverviewOutDTO;
import com.cofinprobootcamp.backend.user.dto.UserOutDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Endpoint to find all available roles.
     *
     * @return A {@link RoleOverviewOutDTO} containing any relevant information about all roles
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
