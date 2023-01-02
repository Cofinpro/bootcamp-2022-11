package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.exceptions.UserNotFoundException;
import com.cofinprobootcamp.backend.profile.Profile;
import com.cofinprobootcamp.backend.enums.StandardRoles;
import com.cofinprobootcamp.backend.role.Role;
import com.cofinprobootcamp.backend.user.dto.UserCreateInDTO;
import com.cofinprobootcamp.backend.user.dto.UserOutDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }
    public User detachProfileFromUser(Long id) {
        User user = userRepository.findById(id).get();
        user.setProfile(null);
        return userRepository.saveAndFlush(user);
    }
    public User assignProfileToUser(User user, Profile profile) {
        user.setProfile(profile);
        return userRepository.saveAndFlush(user);
    }

    public User createUser(UserCreateInDTO inDTO, Role role) {
        String password = passwordEncoder.encode(inDTO.password());
        User user = UserDirector.CreateInDTOToEntity(inDTO, password, role);
        return userRepository.saveAndFlush(user);
    }

    public void deleteUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException();
        }
    }

    public UserOutDTO getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return new UserOutDTO(userOptional.orElseThrow(UserNotFoundException::new));
    }

    public User getUserByUsername(String email) {
        Optional<User> userOptional = userRepository.findByUsername(email);
        return userOptional.orElseThrow(UserNotFoundException::new);
    }

    public List<UserOutDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserOutDTO::new).toList();
    }

    public List<String> getAllUserRoles() {
        return StandardRoles.getAllDefinedValuesAsString();
    }
}
