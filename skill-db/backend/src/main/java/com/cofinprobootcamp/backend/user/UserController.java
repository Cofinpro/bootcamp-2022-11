package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.user.dto.UserCreateInDTO;
import com.cofinprobootcamp.backend.user.dto.UserOutDTO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "")
    public void createUser(@RequestBody @Valid UserCreateInDTO user) {
        userService.createUser(user);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @GetMapping(path = "/{id}")
    public UserOutDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
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
