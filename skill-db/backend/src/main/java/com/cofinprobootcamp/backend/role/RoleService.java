package com.cofinprobootcamp.backend.role;

import com.cofinprobootcamp.backend.exceptions.RoleNotFoundException;
import com.cofinprobootcamp.backend.role.dto.RoleDetailsOutDTO;
import com.cofinprobootcamp.backend.role.dto.RoleOverviewOutDTO;
import com.cofinprobootcamp.backend.user.User;
import com.cofinprobootcamp.backend.user.UserDirector;
import com.cofinprobootcamp.backend.user.UserRepository;
import com.cofinprobootcamp.backend.user.dto.UserOutDTO;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Role service.
 *
 * @version 2.0
 */
@Service
public class RoleService {

    private final UserRepository userRepository;

    public RoleService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public RoleDetailsOutDTO getRoleByIdentifier(String identifier) throws RoleNotFoundException {
        StandardRoles role = StandardRoles.fromIdentifier(identifier);
        if (role.equals(StandardRoles.UNDEFINED)) {
            throw new RoleNotFoundException(identifier);
        }
        return RoleDirector.roleDetailsViewFromEnumType(role);
    }

    public List<RoleOverviewOutDTO> getAllRoles() {
        return RoleDirector.allRoleOverviewsFromEnum();
    }

    public List<UserOutDTO> getUsersByRole(String identifier) throws RoleNotFoundException {
        StandardRoles role = StandardRoles.fromIdentifier(identifier);
        if (role.equals(StandardRoles.UNDEFINED)) {
            throw new RoleNotFoundException(identifier);
        }
        List<User> users = userRepository.findAllByRole(role);
        List<UserOutDTO> usersDTO = new LinkedList<>();
        for (User user : users) {
            usersDTO.add(UserDirector.EntityToUserOutDTO(user));
        }
        return usersDTO;
    }
}
