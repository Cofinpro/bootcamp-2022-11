package com.cofinprobootcamp.backend.role;

import com.cofinprobootcamp.backend.role.dto.RoleCreateInDTO;
import com.cofinprobootcamp.backend.role.dto.RoleOutDTO;
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
    public void createRole(@RequestBody RoleCreateInDTO roleIn) {
        roleService.createRole(roleIn);
    }

    @DeleteMapping(path = "{shortName}")
    public void deleteRoleById(@PathVariable String shortName){
        roleService.deleteRoleByName(shortName);
    }

    @GetMapping(path = "{shortName}")
    public RoleOutDTO getRoleById(@PathVariable String shortName) {
        return roleService.getRoleDTOByName(shortName);
    }

    @GetMapping(path = "")
    public List<RoleOutDTO> getAllRoles() {
        return roleService.getAllRoleDTOs();
    }

}
