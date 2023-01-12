package com.cofinprobootcamp.backend.approval;

import com.cofinprobootcamp.backend.config.Constants;
import com.cofinprobootcamp.backend.user.User;
import com.cofinprobootcamp.backend.utils.RandomStringGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class FourEyesApprovalManager<P extends PendingOperation<?>> implements OperationApprovalManager<P> {

    private final TheApprovalHandler approvalHandler;
    private final List<OperationDetails> managedOperations;
    private String identifier;

    private record OperationDetails(PendingOperation<?> pending, User user, Object[] params) {
    }

    public FourEyesApprovalManager(TheApprovalHandler approvalHandler) {
        this.approvalHandler = approvalHandler;
        tryToSetUniqueOuterId();
        managedOperations = new ArrayList<>();
    }

    @Override
    public String identifier() {
        return identifier;
    }

    public boolean present(P pendingOperation, User operationSource, Object... parameters) {
        System.out.println("Reached parent method");
        for (var operation : managedOperations) {
            System.out.printf("Given: %s, %s, %s%n", pendingOperation.getClass().getSimpleName(), operationSource.getOuterId(), Arrays.toString(parameters));
            System.out.printf("Existing: %s, %s, %s%n", operation.pending().getClass().getSimpleName(), operation.user().getOuterId(), Arrays.toString(operation.params()));
            if (pendingOperation.getClass().equals(operation.pending().getClass()) &&
                    Arrays.equals(parameters, operation.params()) &&
                    !operationSource.getOuterId().equals(operation.user().getOuterId())
            ) {
                managedOperations.remove(operation);
                System.out.printf("Found a match! %s, %s%n", operation.user().getOuterId(), Arrays.toString(operation.params()));
                return approve(pendingOperation);
            }
        }
        managedOperations.add(new OperationDetails(pendingOperation, operationSource, parameters));
        System.out.println("No two matching requests. Store Operation as pending.");
        return false;
    }

    public void register() {
        approvalHandler.registerApprovalManager(this);
    }

    private void tryToSetUniqueOuterId() {
        String candidateId = RandomStringGenerator.nextOuterId(Constants.PROFILE_OUTER_ID_LENGTH);
        if (!approvalHandler.isManagerIdUnique(candidateId)) {
            System.out.println("Collision on ID generation [ApprovalManager]! Must roll again.");
            tryToSetUniqueOuterId();
            return;
        }
        identifier = candidateId;
    }
}
