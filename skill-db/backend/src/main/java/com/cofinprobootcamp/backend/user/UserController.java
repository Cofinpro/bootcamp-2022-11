package com.cofinprobootcamp.backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "")
    public void createUser(@RequestBody User user) {
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
    @GetMapping(path = "/expertises")
    public List<String> getAllExpertises() {
        return userService.getAllExpertises();
    }
}
