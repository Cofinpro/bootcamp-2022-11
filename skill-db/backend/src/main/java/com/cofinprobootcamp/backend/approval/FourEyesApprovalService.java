package com.cofinprobootcamp.backend.approval;

import com.cofinprobootcamp.backend.exceptions.InternalOperationFailedException;
import com.cofinprobootcamp.backend.user.User;
import com.cofinprobootcamp.backend.user.UserService;
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
    public boolean presentForApproval(
            PendingOperation<?> pendingOperation,
            OperationApprovalManager<PendingOperation<T>> approvalManager,
            String operationId,
            String operationSourceId,
            Object... operationParams) {
        boolean isApproved = false;
        for (var storedOperation : getAllStoredOperations()) {
            if (storedOperation.getOperationPath().equals(operationId)
                    && storedOperation.getParameters().equals(createParamsString(operationParams))
                    && !storedOperation.getUserId().equals(operationSourceId)
            ) {
                isApproved = approvalManager.approve((PendingOperation<T>) pendingOperation);
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
}
