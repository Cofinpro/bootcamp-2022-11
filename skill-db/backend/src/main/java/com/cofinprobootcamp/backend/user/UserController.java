package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.user.dto.UserCreateInDTO;
import com.cofinprobootcamp.backend.user.dto.UserOutDTO;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_HR')")
    public void createUser(@RequestBody @Valid UserCreateInDTO userIn) {
        userService.createUser(userIn);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_HR')")
    public void deleteUserById(@PathVariable String id) {
        userService.deleteUserByOuterId(id);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_HR')")
    public UserOutDTO getUserById(@PathVariable String id) {
        return userService.getUserByOuterId(id);
    }

    @GetMapping(path = "")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public List<UserOutDTO> getAllUsers() {
        return userService.getAllUsers();
    }
}
