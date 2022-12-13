package com.cofinprobootcamp.backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {
    //Field Injection is not recommended, you can not unit test this!
    //Better to use Constructor based injection
    @Autowired
    private UserService userService;

    @PostMapping(path = "")
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @DeleteMapping(path = "{id}")
    public void deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
    }

    @GetMapping(path = "{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping(path = "")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }


}
