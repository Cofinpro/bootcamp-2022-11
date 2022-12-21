package com.cofinprobootcamp.backend.role;

import com.cofinprobootcamp.backend.exceptions.RoleAlreadyExistsException;
import com.cofinprobootcamp.backend.exceptions.RoleNotFoundException;
import com.cofinprobootcamp.backend.role.dto.RoleCreateInDTO;
import com.cofinprobootcamp.backend.role.dto.RoleOutDTO;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRightRepository userRightRepository;

    public RoleService(RoleRepository roleRepository, UserRightRepository userRightRepository) {
        this.roleRepository = roleRepository;
        this.userRightRepository = userRightRepository;
    }

    public Role createRole(RoleCreateInDTO roleDTO) {
        if (roleRepository.existsByName(roleDTO.shortName())) {
            throw new RoleAlreadyExistsException();
        }
        Role role = RoleDirector.CreateInDTOToEntity(roleDTO);
        List<UserRight> existingRights = new LinkedList<>();
        List<UserRight> newRights = new LinkedList<>();
        for (UserRight userRight : role.getUserRights()) {
            Optional<UserRight> existingRight = userRightRepository
                    .findUserRightByNamespaceAndOperationAndScope(userRight.getNamespace(), userRight.getOperation(),
                            userRight.getScope());
            if (existingRight.isPresent()) {
                existingRights.add(existingRight.get());
            } else {
                newRights.add(userRight);
            }
        }
        userRightRepository.saveAllAndFlush(newRights);
        newRights.addAll(existingRights);
        role.setUserRights(newRights);
        return roleRepository.saveAndFlush(role);
    }

    public Role saveRole(Role role) {
        userRightRepository.saveAllAndFlush(role.getUserRights());
        return roleRepository.saveAndFlush(role);
    }

    public RoleOutDTO getRoleDTOByName(String name) {
        Optional<Role> roleOptional = roleRepository.findRoleByName(name);
        return new RoleOutDTO(roleOptional.orElseThrow(RoleNotFoundException::new));
    }

    public List<RoleOutDTO> getAllRoleDTOs() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(RoleOutDTO::new).toList();
    }

    public Optional<Role> getRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public void deleteRoleByName(String name) {
        roleRepository.deleteRoleByName(name);
    }
}
