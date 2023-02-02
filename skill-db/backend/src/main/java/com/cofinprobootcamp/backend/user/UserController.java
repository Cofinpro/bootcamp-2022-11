package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.approval.*;
import com.cofinprobootcamp.backend.approval.dto.LockOperationsOutDTO;
import com.cofinprobootcamp.backend.approval.dto.RoleOperationsOutDTO;
import com.cofinprobootcamp.backend.exceptions.LockStatusChangePendingException;
import com.cofinprobootcamp.backend.exceptions.ProfileNotFoundException;
import com.cofinprobootcamp.backend.exceptions.RoleChangePendingException;
import com.cofinprobootcamp.backend.exceptions.UserNotFoundException;
import com.cofinprobootcamp.backend.role.StandardRoles;
import com.cofinprobootcamp.backend.user.dto.UserOutDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {
    private final UserService userService;
    private final FourEyesApprovalService<User> approvalService;

    public UserController(UserService userService, FourEyesApprovalService<User> approvalService) {
        this.userService = userService;
        this.approvalService = approvalService;
    }

    /**
     * Endpoint to find a User by their outerId.
     *
     * @return A {@link UserOutDTO} containing any relevant information about the {@link User}
     */
    @GetMapping(path = "/{id}")
    @PreAuthorize("hasPermission(#id, 'UserOutDTO', @authorityPrefix + 'USERS_GET_BY_ID')")
    public UserOutDTO getUserById(@PathVariable String id) {
        return userService.getUserByOuterId(id);
    }

    /**
     * Endpoint to get the information, if a specific user identified by their outerId is locked or not.
     *
     * @return A {@code boolean} representing the lock status of the {@link User}
     */
    @GetMapping(path = "/{id}/locked")
    @PreAuthorize("hasPermission(#id, 'String', @authorityPrefix + 'USERS_BY_ID_GET_LOCKED')")
    public boolean getLockStatusByUserId(@PathVariable String id) throws UserNotFoundException {
        return userService.getUserByOuterId(id).locked();
    }

    /**
     * Endpoint to find the Profile of a User by their outerId.
     *
     * @return A {@link String} containing the outerId of a {@code Profile}
     */
    @GetMapping(path = "/{id}/profile")
    @PreAuthorize("hasPermission(#id, 'String', @authorityPrefix + 'USERS_BY_ID_GET_PROFILE')")
    public String getProfileByUserId(@PathVariable String id) throws ProfileNotFoundException {
        return userService.getProfileOuterIdByUserOuterId(id);
    }

    /**
     * Endpoint to get the information, if a specific User has a Profile.
     *
     * @return A {@code boolean} representing the existence of a {@code Profile} for this {@link User}
     */
    @GetMapping(path = "/{id}/profile/exists")
    @PreAuthorize("hasPermission(#id, 'boolean', @authorityPrefix + 'USERS_BY_ID_GET_PROFILE_EXISTS')")
    public boolean hasUserProfile(@PathVariable String id) {
        return userService.hasUserAProfile(id);
    }

    /**
     * Endpoint to find all Users.
     *
     * @return A {@link List} of {@link UserOutDTO} representations of each {@link User}
     */
    @GetMapping(path = "")
    @PreAuthorize("hasAuthority(@authorityPrefix + 'USERS_GET_ALL')")
    public List<UserOutDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Endpoint to change the role of a specific user to a new role.
     *
     * @param id The unique outerId of the user
     * @param roleName The unique displayName of the role
     */
    @PatchMapping(path = "/{id}/{roleName}")
    @PreAuthorize("hasAuthority(@authorityPrefix + 'USERS_BY_ID_PATCH_ROLE_BY_NAME')")
    public void changeRole(@PathVariable String id, @PathVariable String roleName) throws RoleChangePendingException{
        PendingOperation<User> method = () -> userService.changeRole(id, roleName);
        boolean isApproved = approvalService
                .checkOperationWithFourEyesPrinciple(
                        method,
                        "USERS_BY_ID_PATCH_ROLE_BY_NAME",
                        id,
                        StandardRoles.ADMIN,
                        id,
                        roleName);
        if (!isApproved) {
            //TODO: Theoretisch keine exception sondern in response schreiben
            throw new RoleChangePendingException();
        }
    }

    /**
     * Endpoint to reverse the lock status of a specific user.
     */
    @PatchMapping(path = "/{id}/lock")
    @PreAuthorize("hasAuthority(@authorityPrefix + 'USERS_BY_ID_PATCH_LOCK')")
    public void lockUser(@PathVariable String id) {
        PendingOperation<User> method = () -> userService.lockUser(id);
        boolean isApproved = approvalService.checkOperationWithFourEyesPrinciple(method, "USERS_BY_ID_PATCH_LOCK", id, StandardRoles.ADMIN, id);
        if (!isApproved) {
            throw new LockStatusChangePendingException();
        }
    }

    /**
     * Endpoint to get all pending role changes.
     *
     * @return A {@link List} of {@link RoleOperationsOutDTO} representations of pending role changing operations.
     */
    @GetMapping(path = "/pending/role")
    @PreAuthorize("hasAuthority(@authorityPrefix + 'USERS_GET_ALL_PENDING_ROLE')")
    public List<RoleOperationsOutDTO> getAllRoleOperations() {
        return approvalService.getAllRoleOperations();
    }

    /**
     * Endpoint to get all pending lock status changes.
     *
     * @return A {@link List} of {@link LockOperationsOutDTO} representations of pending lock status changing operations.
     */
    @GetMapping(path = "/pending/lock")
    @PreAuthorize("hasAuthority(@authorityPrefix + 'USERS_GET_ALL_PENDING_LOCK')")
    public List<LockOperationsOutDTO> getAllLockOperations() {
        return approvalService.getAllLockOperations();
    }

}
