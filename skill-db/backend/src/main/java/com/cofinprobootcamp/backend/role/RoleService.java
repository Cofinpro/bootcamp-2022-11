package com.cofinprobootcamp.backend.role;

import com.cofinprobootcamp.backend.exceptions.RoleNotFoundException;
import com.cofinprobootcamp.backend.profile.Profile;
import com.cofinprobootcamp.backend.profile.dto.ProfileOverviewOutDTO;
import com.cofinprobootcamp.backend.role.dto.RoleDetailsOutDTO;
import com.cofinprobootcamp.backend.role.dto.RoleOverviewOutDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Role service.
 *
 * @version 2.0
 */
@Service
public class RoleService {
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
}
