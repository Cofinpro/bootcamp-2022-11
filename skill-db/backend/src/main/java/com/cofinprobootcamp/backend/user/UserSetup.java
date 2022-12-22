package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.enums.StandardRoles;
import com.cofinprobootcamp.backend.role.Role;
import com.cofinprobootcamp.backend.role.RoleService;
import com.cofinprobootcamp.backend.user.dto.UserCreateInDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
@Order(3)
public class UserSetup {
    private final UserService userService;
    private final RoleService roleService;


    @Autowired
    public UserSetup(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {
        initTestUsers();
    }

    private void initTestUsers() {
        List<UserCreateInDTO> testUsers = new LinkedList<>();
        testUsers.add(dtoFromMinimalInfo(
                "lennart.rehmer",
                "ADMIN"));
        testUsers.add(dtoFromMinimalInfo(
                "markus.kremer",
                "USER"));
        testUsers.add(dtoFromMinimalInfo(
                "luis.geyer",
                "USER"));
        testUsers.add(dtoFromMinimalInfo(
                "thorben.dreier",
                "USER"));
        testUsers.add(dtoFromMinimalInfo(
                "theresa.riesterer",
                "USER"));

        testUsers.forEach(this::createOneTestUser);
    }

    private void createOneTestUser(UserCreateInDTO inDTO) {
        Optional<Role> roleOptional = roleService.getRoleByName(inDTO.userRole());
        if (roleOptional.isPresent()) {
            userService.createUser(inDTO, roleOptional.get());
        } else {
            Role role = StandardRoles.fromShortName(inDTO.userRole()).createNewRoleEntity();
            roleService.saveRole(role);
            userService.createUser(inDTO, role);
        }
    }

    private UserCreateInDTO dtoFromMinimalInfo(String mailBeforeAt, String role) {
        return new UserCreateInDTO(mailBeforeAt + "@cofinpro.de", "mega_gutes_passwort1", role);
    }
}
