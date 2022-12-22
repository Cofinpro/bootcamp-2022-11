package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.profile.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    public void initialize() {
        MockitoAnnotations.openMocks(this);
        this.userService = new UserService(
                passwordEncoder,
                userRepository
        );
    }
    @Test
    void detachProfileFromUser() {
        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(new User(1L, "a","b",true,null,new Profile())));
        assertThat(userService.detachProfileFromUser(1L).getProfile()).isEqualTo(null);
    }

    @Test
    void assignProfileToUser() {
    }

    @Test
    void createUser() {
    }

    @Test
    void deleteUserById() {
    }

    @Test
    void getUserById() {
    }

    @Test
    void getUserByUsername() {
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void getAllUserRoles() {
    }
}
