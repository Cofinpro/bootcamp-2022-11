package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.user.dto.UserCreateInDTO;
import com.cofinprobootcamp.backend.user.dto.UserOutDTO;
import org.springframework.http.HttpStatus;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    @PreAuthorize("hasPermission(#userIn, @authorityPrefix + 'USERS_POST_NEW')")
    public void createUser(@RequestBody @Valid UserCreateInDTO userIn) {
        userService.createUser(userIn);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasPermission(#id, 'void', @authorityPrefix + 'USERS_DELETE_BY_ID')")
    public void deleteUserById(@PathVariable String id) {
        userService.deleteUserByOuterId(id);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasPermission(#id, 'UserOutDTO', @authorityPrefix + 'USERS_GET_BY_ID')")
    public UserOutDTO getUserById(@PathVariable String id) {
        return userService.getUserByOuterId(id);
    }

    @GetMapping(path = "/{id}/profile")
    @PreAuthorize("hasPermission(#id, 'boolean', @authorityPrefix + 'USERS_BY_ID_GET_HAS_PROFILE')")
    public boolean hasUserProfile(@PathVariable String id) {
        return userService.hasUserAProfile(id);
    }

    @GetMapping(path = "")
    @PreAuthorize("hasAuthority(@authorityPrefix + 'USERS_GET_ALL')")
    public List<UserOutDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PatchMapping(path = "/{id}/{roleId}")
    @PreAuthorize("hasAuthority(@authorityPrefix + 'USERS_BY_ID_PATCH_ROLE_BY_ID')")
    public void changeRole(@PathVariable String id, @PathVariable String roleId){
        userService.changeRole(id, roleId);
    }
}
