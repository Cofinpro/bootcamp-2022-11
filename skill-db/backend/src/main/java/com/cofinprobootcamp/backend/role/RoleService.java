package com.cofinprobootcamp.backend.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void createRole(Role role) {
        roleRepository.saveAndFlush(role);
    }

    public RoleDTO getRoleById(Long id) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        return new RoleDTO(roleOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(RoleDTO::new).toList();
    }

    public void deleteRoleById(Long id) {
        roleRepository.deleteById(id);
    }
}
