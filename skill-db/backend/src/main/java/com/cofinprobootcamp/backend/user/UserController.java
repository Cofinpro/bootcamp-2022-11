package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.approval.FourEyesApprovalService;
import com.cofinprobootcamp.backend.approval.OperationApprovalManager;
import com.cofinprobootcamp.backend.approval.PendingOperation;
import com.cofinprobootcamp.backend.auth.CustomJwtAuthenticationToken;
import com.cofinprobootcamp.backend.exceptions.LockStatusChangePendingException;
import com.cofinprobootcamp.backend.exceptions.ProfileNotFoundException;
import com.cofinprobootcamp.backend.exceptions.RoleChangePendingException;
import com.cofinprobootcamp.backend.role.StandardRoles;
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
    private final FourEyesApprovalService<User> approvalService;

    public UserController(UserService userService, FourEyesApprovalService<User> approvalService) {
        this.userService = userService;
        this.approvalService = approvalService;
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
    public void changeRole(@PathVariable String id, @PathVariable String roleId) throws RoleChangePendingException{
        PendingOperation<User> method = () -> userService.changeRole(id, roleId);
        boolean isApproved = checkOperationWithFourEyesPrinciple(method, "USERS_BY_ID_PATCH_ROLE_BY_ID", id, StandardRoles.ADMIN, id, roleId);
        if (!isApproved) {
            throw new RoleChangePendingException();
        }
    }

    @PatchMapping(path = "/{id}/lock")
    @PreAuthorize("hasAuthority(@authorityPrefix + 'USERS_BY_ID_PATCH_LOCK')")
    public void lockUser(@PathVariable String id) {
        PendingOperation<User> method = () -> userService.lockUser(id);
        boolean isApproved = checkOperationWithFourEyesPrinciple(method, "USERS_BY_ID_PATCH_LOCK", id, StandardRoles.ADMIN, id);
        if (!isApproved) {
            throw new LockStatusChangePendingException();
        }
    }

    /**
     * Checks whether the given operation (can be any function wrapped inside an
     * instance of {@link PendingOperation}) may be evaluated and executed or not.
     * In particular, the verification uses the "4-eyes principle" to ensure that
     * only operations are approved that were called by different authorized users
     * on the same resource with the same parameters by implementing a custom
     * {@link OperationApprovalManager}.
     * <br>
     * This is a custom specialization for the "/users" endpoint that supports
     * handling operation with and on {@link User} objects specifically.
     *
     * @param method        An instance of {@link PendingOperation} that holds the method to be
     *                      executed eventually through this endpoint call
     * @param methodPostfix A {@link String} representing the unique endpoint method calling
     *                      this function
     * @param userIdToEdit  A {@link String} representing the {@link User} to be edited or returned
     *                      by {@code method}
     * @param roleToCheck   An instance of {@link StandardRoles} that determines for which user
     *                      roles that check should apply
     * @param params        An array of parameters that will be passed to the method (of type
     *                      {@link Object})
     * @return {@code true}, if the operation was approved and successfully executed,
     * else {@code false}
     */
    private boolean checkOperationWithFourEyesPrinciple(PendingOperation<User> method,
                                                        String methodPostfix,
                                                        String userIdToEdit,
                                                        StandardRoles roleToCheck,
                                                        Object... params) {
        UserOutDTO user = userService.getUserByOuterId(userIdToEdit);
        OperationApprovalManager<PendingOperation<User>> methodManager = pendingOperation -> {
            pendingOperation.resolve();
            return true;
        };
        if (!user.role().identifier().equals(roleToCheck.name())) {
            return methodManager.approve(method);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isApproved = false;
        if (authentication instanceof CustomJwtAuthenticationToken customAuth) {
            isApproved = approvalService.presentForApproval(method,
                    methodManager,
                    methodPostfix,
                    customAuth.getOuterId(),
                    params);
        }
        return isApproved;
    }
}
