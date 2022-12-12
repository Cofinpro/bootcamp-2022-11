package com.cofinprobootcamp.backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "")
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @DeleteMapping(path = "")
    public void deleteUser(@RequestBody User user){
        userService.deleteUser(user);
    }

    @GetMapping(path = "/")
    public UserDTO getUser(@RequestParam Long id) {
        return userService.getUserById(id);
    }

    @GetMapping(path = "")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }


}
