package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.config.Constants;
import com.cofinprobootcamp.backend.exceptions.UserCreationFailedException;
import com.cofinprobootcamp.backend.exceptions.UserNotFoundException;
import com.cofinprobootcamp.backend.profile.Profile;
import com.cofinprobootcamp.backend.enums.StandardRoles;
import com.cofinprobootcamp.backend.role.Role;
import com.cofinprobootcamp.backend.user.dto.UserCreateInDTO;
import com.cofinprobootcamp.backend.user.dto.UserOutDTO;
import com.cofinprobootcamp.backend.utils.RandomStringGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            System.out.println("User not found on deleting profile. This should logically not happen");
            throw new UserNotFoundException();
        }
        User user = userOptional.get();
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
        try {
            tryToSetUniqueOuterId(user);
            userRepository.saveAndFlush(user);
        } catch (Exception e) {
            throw new UserCreationFailedException();
        }
        return user;
    }

    @Transactional
    public void deleteUserByOuterId(String outerId) {
        Optional<User> userOptional = userRepository.findFirstByOuterId(outerId);
        if (userOptional.isPresent()) {
            userRepository.deleteByOuterId(outerId);
        } else {
            throw new UserNotFoundException();
        }
    }

    public UserOutDTO getUserByOuterId(String outerId) {
        Optional<User> userOptional = userRepository.findFirstByOuterId(outerId);
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

    private void tryToSetUniqueOuterId(User user) {
        String candidateId = RandomStringGenerator.nextOuterId(Constants.USER_OUTER_ID_LENGTH);
        Optional<User> userOptional = userRepository.findFirstByOuterId(candidateId);
        if (userOptional.isPresent()) {
            System.out.println("Collision on id generation [User]! Must roll again.");
            tryToSetUniqueOuterId(user);
            return;
        }
        user.setOuterId(candidateId);
    }
}
