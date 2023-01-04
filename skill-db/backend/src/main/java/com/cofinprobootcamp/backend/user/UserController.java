package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.enums.StandardRoles;
import com.cofinprobootcamp.backend.exceptions.RoleNotFoundException;
import com.cofinprobootcamp.backend.role.Role;
import com.cofinprobootcamp.backend.role.RoleService;
import com.cofinprobootcamp.backend.user.dto.UserCreateInDTO;
import com.cofinprobootcamp.backend.user.dto.UserOutDTO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping(path = "")
    public void createUser(@RequestBody @Valid UserCreateInDTO userIn) {
        if (userIn.userRole() == null) {
            userService.createUser(userIn, StandardRoles.USER.createNewRoleEntity());
            return;
        }
        Optional<Role> roleOptional = roleService.getRoleByName(userIn.userRole());
        if (roleOptional.isPresent()) {
            userService.createUser(userIn, roleOptional.get());
            return;
        }
        StandardRoles standardRoleType = StandardRoles.fromShortName(userIn.userRole());
        if (StandardRoles.UNDEFINED.equals(standardRoleType)) {
            throw new RoleNotFoundException();
        }
        Role role = standardRoleType.createNewRoleEntity();
        roleService.saveRole(role);
        userService.createUser(userIn, role);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteUserById(@PathVariable String id) {
        userService.deleteUserByOuterId(id);
    }

    @GetMapping(path = "/{id}")
    public UserOutDTO getUserById(@PathVariable String id) {
        return userService.getUserByOuterId(id);
    }

    @GetMapping(path = "")
    public List<UserOutDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/roles")
    public List<String> getAllUserRoles() {
        return userService.getAllUserRoles();
    }
}
