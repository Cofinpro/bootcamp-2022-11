package com.cofinprobootcamp.backend.role;

import com.cofinprobootcamp.backend.role.dto.RoleInOutDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void createRole(Role role) {
        roleRepository.saveAndFlush(role);
    }

    public RoleInOutDTO getRoleById(Long id) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        return new RoleInOutDTO(roleOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    public List<RoleInOutDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(RoleInOutDTO::new).toList();
    }

    public void deleteRoleById(Long id) {
        roleRepository.deleteById(id);
    }
}
