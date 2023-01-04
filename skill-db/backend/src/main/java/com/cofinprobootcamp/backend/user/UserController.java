package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.role.RoleService;
import com.cofinprobootcamp.backend.user.dto.UserCreateInDTO;
import com.cofinprobootcamp.backend.user.dto.UserOutDTO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
        userService.createUser(userIn);
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
