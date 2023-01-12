package com.cofinprobootcamp.backend.approval;

import com.cofinprobootcamp.backend.exceptions.InternalOperationFailedException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TheApprovalHandler {
    private final List<OperationApprovalManager<?>> approvalManagers = new ArrayList<>();

    public void registerApprovalManager(OperationApprovalManager<?> manager) {
        if (isManagerIdUnique(manager.identifier())) {
            approvalManagers.add(manager);
            System.out.println(approvalManagers);
        } else {
            throw new InternalOperationFailedException(
                    "Interner Fehler. Ursache ist möglicherweise eine Race Condition. Bitte erneut versuchen!",
                    null
            );
        }
    }

    public boolean isManagerIdUnique(String managerId) {
        return approvalManagers.stream()
                .noneMatch(m -> m.identifier().equals(managerId));
    }

    public OperationApprovalManager<?> findManagerById(String id) {
        return approvalManagers.stream()
                .filter(m -> m.identifier().equals(id))
                .findFirst()
                .orElse(null);
    }
}
