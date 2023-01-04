package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.exceptions.RoleNotFoundException;
import com.cofinprobootcamp.backend.role.StandardRoles;
import com.cofinprobootcamp.backend.role.RoleService;
import com.cofinprobootcamp.backend.user.dto.UserCreateInDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

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
        try {
            userService.createUser(inDTO);
        } catch (RoleNotFoundException e) {
            userService.createUser(
                    new UserCreateInDTO(
                            inDTO.email(),
                            inDTO.password(),
                            StandardRoles.USER.toString()
                    )
            );
        }
    }

    private UserCreateInDTO dtoFromMinimalInfo(String mailBeforeAt, String role) {
        return new UserCreateInDTO(mailBeforeAt + "@cofinpro.de", "mega_gutes_passwort1", role);
    }
}
