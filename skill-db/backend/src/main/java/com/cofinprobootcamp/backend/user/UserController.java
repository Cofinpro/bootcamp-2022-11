package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.approval.OperationApprovalManager;
import com.cofinprobootcamp.backend.approval.PendingOperation;
import com.cofinprobootcamp.backend.exceptions.ProfileNotFoundException;
import com.cofinprobootcamp.backend.exceptions.RoleChangePendingException;
import com.cofinprobootcamp.backend.user.dto.UserCreateInDTO;
import com.cofinprobootcamp.backend.user.dto.UserOutDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @PreAuthorize("hasPermission(#id, 'String', @authorityPrefix + 'USERS_BY_ID_GET_PROFILE')")
    public String getProfileByUserId(@PathVariable String id) throws ProfileNotFoundException {
        return userService.getProfileOuterIdByUserOuterId(id);
    }

    @GetMapping(path = "/{id}/profile/exists")
    @PreAuthorize("hasPermission(#id, 'boolean', @authorityPrefix + 'USERS_BY_ID_GET_PROFILE_EXISTS')")
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
        PendingOperation<User> method = () -> {
            System.out.printf("We reached 'method' with values [id: %s, roleId: %s]%n", id, roleId);
            return userService.changeRole(id, roleId);
        };
        OperationApprovalManager<PendingOperation<User>> methodManager;
        if (approvalManagers.containsKey("changeRole")) {
            System.out.println("Load existing Manager");
            methodManager = approvalManagers.get("changeRole");
        } else {
            System.out.println("Create new Manager");
            methodManager = new OperationApprovalManager<PendingOperation<User>>() {
                @Override
                public boolean approve(PendingOperation<User> pendingOperation) {
                    System.out.printf("Called approve() with %s%n", pendingOperation.getClass().getSimpleName());
                    pendingOperation.resolve();
                    return true;
                }
            };
            methodManager.register();
            approvalManagers.put("changeRole", methodManager);
        }
        System.out.println("Method manager object: " + methodManager);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Read auth: " + authentication.getName());
        if (!methodManager.presentForApproval(method, userService.getUserByUsername(authentication.getName()), id, roleId)) {
            throw new RoleChangePendingException();
        }
    }

    @PatchMapping(path = "/{id}/lock")
    @PreAuthorize("hasAuthority(@authorityPrefix + 'USERS_BY_ID_PATCH_LOCK')")
    public void lockUser(@PathVariable String id){
        userService.lockUser(id);
    }
}
