package com.cofinprobootcamp.backend.role;

import com.cofinprobootcamp.backend.role.dto.RoleInOutDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/roles")
public class RoleController {
    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    @PostMapping(path = "")
    public void createRole(@RequestBody Role role) {
        roleService.createRole(role);
    }

    @DeleteMapping(path = "{id}")
    public void deleteRoleById(@PathVariable Long id){
        roleService.deleteRoleById(id);
    }

    @GetMapping(path = "{id}")
    public RoleInOutDTO getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    @GetMapping(path = "")
    public List<RoleInOutDTO> getAllRoles() {
        return roleService.getAllRoles();
    }

}
