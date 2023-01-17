package com.cofinprobootcamp.backend.approval;

import com.cofinprobootcamp.backend.exceptions.InternalOperationFailedException;
import com.cofinprobootcamp.backend.profile.Profile;
import com.cofinprobootcamp.backend.role.StandardRoles;
import com.cofinprobootcamp.backend.user.User;
import com.cofinprobootcamp.backend.user.UserService;
import org.apache.poi.ss.formula.functions.T;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FourEyesApprovalServiceTest {

    @Mock
    private StoredOperationRepository operationRepository;
    @Mock
    private UserService userService;

    private FourEyesApprovalService<T> approvalService;

    @BeforeEach
    public void initialize() {
        MockitoAnnotations.openMocks(this);
        this.approvalService = new FourEyesApprovalService(
                operationRepository,
                userService
        );
    }

    @Test
    @DisplayName("Test for creating operation")
    void createOperationWhenNotExisting() {

        String outerId = "1";
        User user = new User(1L, outerId, "test@test.com", "123", false, StandardRoles.USER, new Profile());

        PendingOperation<User> method = () -> userService.changeRole(outerId, "Administrator");
        OperationApprovalManager<PendingOperation<User>> methodManager = pendingOperation -> {
            pendingOperation.resolve();
            return true;
        };

        Mockito.when(userService.getIdByOuterId(outerId)).thenReturn(1L);
        Mockito.when(userService.getUserById(1L)).thenReturn(user);
        Mockito.when(operationRepository.findAll()).thenReturn(List.of());
        Mockito.when(operationRepository.findAllByOperationPathAndUserAndParameters("USERS_BY_ID_PATCH_ROLE_BY_NAME", user, "abc;"))
                .thenReturn(List.of());
        ArgumentCaptor<StoredOperation> argumentCaptor = ArgumentCaptor.forClass(StoredOperation.class);

        boolean isApproved = approvalService.presentForApproval(method, methodManager,"USERS_BY_ID_PATCH_ROLE_BY_NAME", outerId, "abc");

        Mockito.verify(operationRepository,Mockito.times(1)).saveAndFlush(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getParameters()).isEqualTo("abc;");
        assertThat(isApproved).isFalse();
    }

    @Test
    @DisplayName("Test for deleting operation")
    void deleteOperationWhenExisting() {

        String outerId = "1";
        User user = new User(1L, outerId, "test@test.com", "123", false, StandardRoles.USER, new Profile());
        StoredOperation operation = new StoredOperation(1L,"USERS_BY_ID_PATCH_ROLE_BY_NAME", user,"abc;");

        PendingOperation<User> method = () -> userService.changeRole(outerId, "Administrator");
        OperationApprovalManager<PendingOperation<User>> methodManager = pendingOperation -> {
            pendingOperation.resolve();
            return true;
        };

        Mockito.when(operationRepository.findAll()).thenReturn(List.of(operation));
        Mockito.when(operationRepository.findById(1L)).thenReturn(Optional.of(operation));

        boolean isApproved = approvalService.presentForApproval(method, methodManager,"USERS_BY_ID_PATCH_ROLE_BY_NAME", "2", "abc");

        Mockito.verify(operationRepository,Mockito.times(1)).deleteById(1L);
        assertThat(isApproved).isTrue();
    }

    @Test
    @DisplayName("Test for deleting same existing operation")
    void deleteSameOperationWhenExisting() {

        String outerId = "1";
        User user = new User(1L, outerId, "test@test.com", "123", false, StandardRoles.USER, new Profile());
        StoredOperation operation = new StoredOperation(1L,"USERS_BY_ID_PATCH_ROLE_BY_NAME", user,"abc;");

        PendingOperation<User> method = () -> userService.changeRole(outerId, "Administrator");
        OperationApprovalManager<PendingOperation<User>> methodManager = pendingOperation -> {
            pendingOperation.resolve();
            return true;
        };

        Mockito.when(userService.getIdByOuterId(outerId)).thenReturn(1L);
        Mockito.when(userService.getUserById(1L)).thenReturn(user);
        Mockito.when(operationRepository.findAll()).thenReturn(List.of(operation));
        Mockito.when(operationRepository.findAllByOperationPathAndUserAndParameters("USERS_BY_ID_PATCH_ROLE_BY_NAME", user, "abc;"))
                .thenReturn(List.of(operation));
        Mockito.when(operationRepository.findById(1L)).thenReturn(Optional.of(operation));

        ArgumentCaptor<StoredOperation> argumentCaptor = ArgumentCaptor.forClass(StoredOperation.class);

        boolean isApproved = approvalService.presentForApproval(method, methodManager,"USERS_BY_ID_PATCH_ROLE_BY_NAME", outerId, "abc");

        Mockito.verify(operationRepository,Mockito.times(1)).saveAndFlush(argumentCaptor.capture());
        Mockito.verify(operationRepository,Mockito.times(1)).deleteById(1L);
        assertThat(argumentCaptor.getValue().getParameters()).isEqualTo("abc;");
        assertThat(isApproved).isFalse();
    }

    @Test
    @DisplayName("Test for throwing exception when operation exists two times")
    void throwExceptionWhenTwoTimes() {

        String outerId = "1";
        User user = new User(1L, outerId, "test@test.com", "123", false, StandardRoles.USER, new Profile());
        StoredOperation operation = new StoredOperation(1L,"USERS_BY_ID_PATCH_ROLE_BY_NAME", user,"abc;");

        Mockito.when(userService.getIdByOuterId(outerId)).thenReturn(1L);
        Mockito.when(userService.getUserById(1L)).thenReturn(user);
        Mockito.when(operationRepository.findAllByOperationPathAndUserAndParameters("USERS_BY_ID_PATCH_ROLE_BY_NAME", user, "abc;"))
                .thenReturn(List.of(operation, operation));

        assertThrows(InternalOperationFailedException.class,() -> approvalService.createStoredOperation("USERS_BY_ID_PATCH_ROLE_BY_NAME", outerId, "abc"));
    }
}
