package com.cofinprobootcamp.backend.approval;

import com.cofinprobootcamp.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoredOperationRepository extends JpaRepository<StoredOperation, Long> {
    List<StoredOperation> findAllByOperationPathAndUserAndParameters(String operationPath, User user, String params);
    List<StoredOperation> findAllByOperationPathContains(String operationPath);

}
