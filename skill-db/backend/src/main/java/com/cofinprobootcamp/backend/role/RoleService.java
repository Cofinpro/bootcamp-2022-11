package com.cofinprobootcamp.backend.role;

import com.cofinprobootcamp.backend.exceptions.RoleNotFoundException;
import com.cofinprobootcamp.backend.profile.Profile;
import com.cofinprobootcamp.backend.profile.ProfileRepository;
import com.cofinprobootcamp.backend.profile.dto.ProfileOverviewOutDTO;
import com.cofinprobootcamp.backend.role.dto.RoleDetailsOutDTO;
import com.cofinprobootcamp.backend.role.dto.RoleOverviewOutDTO;
import com.cofinprobootcamp.backend.user.User;
import com.cofinprobootcamp.backend.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<RoleDetailsOutDTO> getAllRoles() {
        return RoleDirector.roleOverviewFromEnum();
    }

    public List<String> getUsersByRole(String identifier) throws RoleNotFoundException {
        StandardRoles role = StandardRoles.fromIdentifier(identifier);
        if (role.equals(StandardRoles.UNDEFINED)) {
            throw new RoleNotFoundException(identifier);
        }
        List<User> users = userRepository.findAllByRole(role);
        List<String> userNames = new ArrayList<>();
        for(User user : users) {
            userNames.add(user.getUsername());
        }
        return userNames;
    }
}
