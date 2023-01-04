package com.cofinprobootcamp.backend.role;

import com.cofinprobootcamp.backend.exceptions.RoleNotFoundException;
import com.cofinprobootcamp.backend.role.dto.RoleDetailsOutDTO;
import com.cofinprobootcamp.backend.role.dto.RoleOverviewOutDTO;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    public RoleDetailsOutDTO getRoleByIdentifier(String identifier) throws RoleNotFoundException {
        StandardRoles role = StandardRoles.fromIdentifier(identifier);
        if (role.equals(StandardRoles.UNDEFINED)) {
            throw new RoleNotFoundException(identifier);
        }
        return RoleDirector.roleDetailsViewFromEnumType(role);
    }

    public RoleOverviewOutDTO getAllRoles() {
        return RoleDirector.roleOverviewFromEnum();
    }
}
