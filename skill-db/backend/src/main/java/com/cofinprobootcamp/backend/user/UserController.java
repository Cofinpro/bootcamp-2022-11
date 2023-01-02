package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.auth.UserDetailsImpl;
import com.cofinprobootcamp.backend.enums.StandardRoles;
import com.cofinprobootcamp.backend.exceptions.RoleNotFoundException;
import com.cofinprobootcamp.backend.role.Role;
import com.cofinprobootcamp.backend.role.RoleService;
import com.cofinprobootcamp.backend.user.dto.UserCreateInDTO;
import com.cofinprobootcamp.backend.user.dto.UserOutDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_HR')")
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
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_HR')")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_HR')")
    public UserOutDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping(path = "")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public List<UserOutDTO> getAllUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getAuthorities());
        return userService.getAllUsers();
    }

    @GetMapping(path = "/roles")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public List<String> getAllUserRoles() {
        return userService.getAllUserRoles();
    }
}
