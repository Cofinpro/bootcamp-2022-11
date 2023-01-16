package com.cofinprobootcamp.backend.approval;

import com.cofinprobootcamp.backend.approval.dto.LockOperationsOutDTO;
import com.cofinprobootcamp.backend.approval.dto.RoleOperationsOutDTO;

public class StoredOperationDirector {

    public static RoleOperationsOutDTO roleOperationsFromStoredOperations(StoredOperation operation) {
        return new RoleOperationsOutDTO(
                operation.getParameters().split(";")[0],
                operation.getUser().getUsername(),
                operation.getParameters().split(";")[1]
        );
    }

    public static LockOperationsOutDTO lockOperationsFromStoredOperations(StoredOperation operation) {
        return new LockOperationsOutDTO(
                operation.getParameters().split(";")[0],
                operation.getUser().getUsername()
        );
    }
}
