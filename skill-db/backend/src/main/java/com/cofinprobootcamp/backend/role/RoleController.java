package com.cofinprobootcamp.backend.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping(path = "")
    public void createRole(@RequestBody Role role) {
        roleService.createRole(role);
    }

    @DeleteMapping(path = "")
    public void deleteRole(@RequestBody Role role){
        roleService.deleteRole(role);
    }

    @GetMapping(path = "/")
    public RoleDTO getRole(@RequestParam Long id) {
        return roleService.getRoleById(id);
    }

    @GetMapping(path = "")
    public List<RoleDTO> getAllRoles() {
        return roleService.getAllRoles();
    }

}
