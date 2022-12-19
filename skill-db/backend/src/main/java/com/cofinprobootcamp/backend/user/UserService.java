package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.enums.Expertises;
import com.cofinprobootcamp.backend.profile.Profile;
import com.cofinprobootcamp.backend.user.dto.UserOutDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // New DTO here
    public void createUser(User user) {
        userRepository.saveAndFlush(user);
    }

    public UserOutDTO getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return new UserOutDTO(userOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(RuntimeException::new);
    }
    public List<UserOutDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserOutDTO::new).toList();
    }
    public User assignProfileToUser(User user, Profile profile) {
        user.setProfile(profile);
        return userRepository.saveAndFlush(user);
    }
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public List<String> getAllExpertises() {
        return Expertises.getAllDefinedValuesAsString();
    }
}
