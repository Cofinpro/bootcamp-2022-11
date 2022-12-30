package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.exceptions.UserNotFoundException;
import com.cofinprobootcamp.backend.profile.Profile;
import com.cofinprobootcamp.backend.role.Role;
import com.cofinprobootcamp.backend.user.dto.UserCreateInDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
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
        User user = new User(1L, "a","b",true,null,new Profile());
        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        userService.detachProfileFromUser(1L);
        Mockito.verify(userRepository,Mockito.times(1)).saveAndFlush(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getProfile()).isNull();
    }

    @Test
    void assignProfileToUser() {
        User user = new User(1L, "a","b",true,null,null);
        Profile profile = new Profile();
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        userService.assignProfileToUser(user,profile);
        Mockito.verify(userRepository,Mockito.times(1)).saveAndFlush(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getProfile()).isNotNull();
    }

    @Test
    void createUser() {
        UserCreateInDTO inDTO = new UserCreateInDTO(
                "e@mail.de",
                "password",
                "ADMIN"
        );
        String encodedPassword = "PASSWORD";
        Mockito.when(passwordEncoder.encode("password"))
                .thenReturn(encodedPassword);
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        Role role = Role.builder().name(inDTO.userRole()).build();
        userService.createUser(inDTO, role);
        Mockito.verify(userRepository, Mockito.times(1)).saveAndFlush(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getUsername()).isEqualTo(inDTO.email());
        assertThat(argumentCaptor.getValue().getPassword()).isEqualTo(encodedPassword);
    }

    @Test
    void deleteUserById() {
        User user = User.builder().username("").build();
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userService.deleteUserById(1L);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(1L);

        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,() -> {userService.deleteUserById(2L);});
    }

    @Test
    void getUserById() {
        User user = User.builder().username("aaaaa").build();
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        assertThat(userService.getUserById(1L).email()).isEqualTo(user.getUsername());

        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,() -> {userService.getUserById(2L);});
    }

    @Test
    void getUserByUsername() {
        User user = User.builder().username("a@b.c").build();
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        assertThat(userService.getUserByUsername(user.getUsername()).getUsername()).isEqualTo(user.getUsername());

        Mockito.when(userRepository.findByUsername("a")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserByUsername("a");
        });
    }

    @Test
    void getAllUsers() {
        User user = User.builder().username("aaaa").build();
        List<User> userList = List.of(user);
        Mockito.when(userRepository.findAll()).thenReturn(userList);
        assertThat(userService.getAllUsers().get(0).email()).isEqualTo(user.getUsername());
    }
}
