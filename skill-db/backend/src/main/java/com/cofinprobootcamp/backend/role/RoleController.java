package com.cofinprobootcamp.backend.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/role")
public class RoleController {
    //Field Injection is not recommended, you can not unit test this!
    //Better to use Constructor based injection
    @Autowired
    private RoleService roleService;

    @PostMapping(path = "")
    public void createRole(@RequestBody Role role) {
        roleService.createRole(role);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteRoleById(@PathVariable Long id){
        roleService.deleteRoleById(id);
    }

    @GetMapping(path = "/{id}")
    public RoleDTO getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    @GetMapping(path = "")
    public List<RoleDTO> getAllRoles() {
        return roleService.getAllRoles();
    }

}
