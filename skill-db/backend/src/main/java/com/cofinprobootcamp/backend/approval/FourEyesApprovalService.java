package com.cofinprobootcamp.backend.approval;

import com.cofinprobootcamp.backend.approval.dto.LockOperationsOutDTO;
import com.cofinprobootcamp.backend.approval.dto.RoleOperationsOutDTO;
import com.cofinprobootcamp.backend.auth.CustomJwtAuthenticationToken;
import com.cofinprobootcamp.backend.exceptions.InternalOperationFailedException;
import com.cofinprobootcamp.backend.role.StandardRoles;
import com.cofinprobootcamp.backend.user.User;
import com.cofinprobootcamp.backend.user.UserService;
import com.cofinprobootcamp.backend.user.dto.UserOutDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class FourEyesApprovalService<T> {
    private final StoredOperationRepository operationRepository;
    private final UserService userService;

    FourEyesApprovalService(StoredOperationRepository operationRepository, UserService userService) {
        this.operationRepository = operationRepository;
        this.userService = userService;
    }

    /**
     * Implements the general logic of the "Four-Eyes Principle".
     * <br>
     * If the presented operation (identified through its operationId, i.e., path, and its
     * source as well as its parameters) is equal to an existing one in any way except its
     * source (i.e., the user who invoked it), then the verification is successful.
     * <br>
     * Otherwise, the operation is stored as pending.
     *
     * @param pendingOperation The {@link PendingOperation} to execute in case of approval
     * @param approvalManager The {@link OperationApprovalManager} that decides with custom logic
     *                        whether to approve the operation
     * @param operationId The unique operation path identifying a method
     * @param operationSourceId The internal ID of a {@link User} (type {@link Long}
     * @param operationParams An array of parameters that were passed to the operation
     * @return {@code true}, if the operation was approved and executed, {@code false} otherwise
     */
    private boolean presentForApproval(
            PendingOperation<User> pendingOperation,
            OperationApprovalManager<PendingOperation<User>> approvalManager,
            String operationId,
            String operationSourceId,
            Object... operationParams) {
        boolean isApproved = false;
        for (var storedOperation : getAllStoredOperations()) {
            if (storedOperation.getOperationPath().equals(operationId)
                    && storedOperation.getParameters().equals(createParamsString(operationParams))
                    && !storedOperation.getUserId().equals(operationSourceId)
            ) {
                isApproved = approvalManager.approve((PendingOperation<User>) pendingOperation);
                deleteStoredOperationById(storedOperation.getId());
            }
        }
        if (!isApproved) {
            createStoredOperation(operationId, operationSourceId, operationParams);
        }
        System.out.printf("Approval status: %b%n", isApproved);
        return isApproved;
    }

    public List<StoredOperation> getAllStoredOperations() {
        return operationRepository.findAll();
    }

    public List<RoleOperationsOutDTO> getAllRoleOperations() {
        List<StoredOperation> operations = operationRepository.findAllByOperationPathContains("ROLE");
        return operations.stream()
                .map(StoredOperationDirector::roleOperationsFromStoredOperations)
                .toList();
    }

    public List<LockOperationsOutDTO> getAllLockOperations() {
        List<StoredOperation> operations = operationRepository.findAllByOperationPathContains("LOCK");
        return operations.stream()
                .map(StoredOperationDirector::lockOperationsFromStoredOperations)
                .toList();
    }

    public StoredOperation createStoredOperation(String operationPath, String userId, Object... params) {
        Long id = userService.getIdByOuterId(userId);
        User user = userService.getUserById(id);
        List<StoredOperation> existing = operationRepository.findAllByOperationPathAndUserAndParameters(operationPath, user, createParamsString(params));
        if (existing.isEmpty()) {
            StoredOperation operation = StoredOperation.builder()
                    .operationPath(operationPath)
                    .user(user)
                    .parameters(createParamsString(params))
                    .build();
            return operationRepository.saveAndFlush(operation);
        }
        if (existing.size() == 1) {
            deleteStoredOperationById(existing.get(0).getId());
            StoredOperation operation = StoredOperation.builder()
                    .operationPath(operationPath)
                    .user(user)
                    .parameters(createParamsString(params))
                    .build();
            return operationRepository.saveAndFlush(operation);
        }
        throw new InternalOperationFailedException("Interner Datenbankfehler. Dies sollte nicht passieren.", null);
    }

    @Transactional
    public void deleteStoredOperationById(Long id) {
        if (operationRepository.findById(id).isPresent()) {
            operationRepository.deleteById(id);
        }
    }

    private String createParamsString(Object... parameters) {
        StringBuilder builder = new StringBuilder();
        for (var object : parameters) {
            builder.append(object.toString());
            builder.append(";");
        }
        return builder.toString();
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
    public boolean checkOperationWithFourEyesPrinciple(PendingOperation<User> method,
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
            isApproved = presentForApproval(method,
                    methodManager,
                    methodPostfix,
                    customAuth.getOuterId(),
                    params);
        }
        return isApproved;
    }
}
