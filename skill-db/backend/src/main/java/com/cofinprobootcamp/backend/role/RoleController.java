package com.cofinprobootcamp.backend.role;

import com.cofinprobootcamp.backend.role.dto.RoleCreateInDTO;
import com.cofinprobootcamp.backend.role.dto.RoleOutDTO;
import com.cofinprobootcamp.backend.role.dto.RoleOverviewOutDTO;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping(path = "")
    @Secured("ROLE_ADMIN")
    public void createRole(@RequestBody RoleCreateInDTO roleIn) {
        roleService.createRole(roleIn);
    }

    @DeleteMapping(path = "{shortName}")
    @Secured("ROLE_ADMIN")
    public void deleteRoleById(@PathVariable String shortName){
        roleService.deleteRoleByName(shortName);
    }

    @GetMapping(path = "{shortName}")
    @Secured("ROLE_ADMIN")
    public RoleOverviewOutDTO getRoleById(@PathVariable String shortName) {
        return roleService.getSimplifiedRoleDTOByName(shortName);
    }

    @GetMapping(path = "")
    @Secured("ROLE_ADMIN")
    public List<RoleOverviewOutDTO> getAllRoles() {
        return roleService.getAllSimplifiedRoleDTOs();
    }

}
