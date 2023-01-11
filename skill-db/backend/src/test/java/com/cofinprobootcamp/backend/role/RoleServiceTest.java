package com.cofinprobootcamp.backend.role;

import com.cofinprobootcamp.backend.role.dto.RoleOverviewOutDTO;
import com.cofinprobootcamp.backend.user.User;
import com.cofinprobootcamp.backend.user.UserDirector;
import com.cofinprobootcamp.backend.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class RoleServiceTest {

    @Mock
    private UserRepository userRepository;
    private RoleService roleService;

    @BeforeEach
    public void initialize() {
        MockitoAnnotations.openMocks(this);
        this.roleService = new RoleService(
                userRepository
        );
    }

    @Test
    void getRoleByIdentifier() {
        StandardRoles roleToFind = StandardRoles.HR;
        String roleIdentifier = roleToFind.name();
        assertThat(roleService.getRoleByIdentifier(roleIdentifier).displayName()).isEqualTo(roleToFind.toString());
    }

    @Test
    void getAllRoles() {
        List<RoleOverviewOutDTO> allRoles = new LinkedList<>();
        for (var role : StandardRoles.getAllDefinedValues()) {
            allRoles.add(new RoleOverviewOutDTO(
                    role.name(),
                    role.toString(),
                    role.getRoleDetailsDescription()
            ));
        }
        assertThat(roleService.getAllRoles()).isEqualTo(allRoles);
    }

    @Test
    void getUsersByRole() {
        StandardRoles roleToFind = StandardRoles.USER;
        String roleIdentifier = roleToFind.name();
        User user1 = User.builder()
                .username("lennart.rehmer@cofinpro.de")
                .role(roleToFind)
                .build();
        User user2 = User.builder()
                .username("theresa.riesterer@cofinpro.de")
                .role(roleToFind)
                .build();
        List<User> users = List.of(user1, user2);
        Mockito.when(userRepository.findAllByRole(roleToFind)).thenReturn(users);
        assertThat(roleService.getUsersByRole(roleIdentifier))
                .contains(
                        UserDirector.EntityToUserOutDTO(user1),
                        UserDirector.EntityToUserOutDTO(user2)
                        );
    }
}