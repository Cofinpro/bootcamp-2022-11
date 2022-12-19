package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.user.dto.UserCreateInDTO;
import com.cofinprobootcamp.backend.user.dto.UserOutDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // New DTO here
    public void createUser(UserCreateInDTO inDTO) {
        User user = UserDirector.CreateInDTOToEntity(inDTO);
        userRepository.saveAndFlush(user);
    }

    public UserOutDTO getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return new UserOutDTO(userOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    public List<UserOutDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserOutDTO::new).toList();
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
