package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.config.Constants;
import com.cofinprobootcamp.backend.exceptions.RoleNotFoundException;
import com.cofinprobootcamp.backend.exceptions.InternalOperationFailedException;
import com.cofinprobootcamp.backend.exceptions.UserAlreadyExistsException;
import com.cofinprobootcamp.backend.exceptions.UserNotFoundException;
import com.cofinprobootcamp.backend.profile.Profile;
import com.cofinprobootcamp.backend.role.StandardRoles;
import com.cofinprobootcamp.backend.user.dto.UserCreateInDTO;
import com.cofinprobootcamp.backend.user.dto.UserOutDTO;
import com.cofinprobootcamp.backend.utils.RandomStringGenerator;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public User createUser(UserCreateInDTO inDTO) {
        if (userRepository.findByUsername(inDTO.email()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        String password = passwordEncoder.encode(inDTO.password());
        StandardRoles role = (inDTO.userRole() != null) ? StandardRoles.fromIdentifier(inDTO.userRole()) : null;
        if (StandardRoles.UNDEFINED.equals(role)) {
            throw new RoleNotFoundException(inDTO.userRole());
        }
        User user = UserDirector.CreateInDTOToEntity(inDTO, password, role);
        try {
            tryToSetUniqueOuterId(user);
            userRepository.saveAndFlush(user);
        } catch (Exception e) {
            String msg = "Nutzer konnte nicht gespeichert werden. Ursache könnte möglicherweise eine Race Condition sein. Bitte erneut versuchen!";
            throw new InternalOperationFailedException(msg, e);
        }
        return user;
    }

    @Transactional
    public void deleteUserByOuterId(String outerId) {
        Optional<User> userOptional = userRepository.findFirstByOuterId(outerId);
        if (userOptional.isPresent()) {
            long deletedUsers = userRepository.deleteByOuterId(outerId);
            if (deletedUsers != 1L) {
                String msg = "Beim Löschen eines Nutzers trat ein schwerwiegendes Problem auf!";
                throw new InternalOperationFailedException(msg,
                        new Exception("The number of deleted users following delete operation on repository was not equal to 1"));
            }
        } else {
            throw new UserNotFoundException();
        }
    }

    public UserOutDTO getUserByOuterId(String outerId) {
        Optional<User> userOptional = userRepository.findFirstByOuterId(outerId);
        return UserDirector.EntityToUserOutDTO(userOptional.orElseThrow(UserNotFoundException::new));
    }

    public UserOutDTO getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return UserDirector.EntityToUserOutDTO(userOptional.orElseThrow(UserNotFoundException::new));
    }

    public User getUserByUsername(String email) {
        Optional<User> userOptional = userRepository.findByUsername(email);
        return userOptional.orElseThrow(() -> new UsernameNotFoundException("Die E-Mail " + email + " ist keinem Nutzer zugewiesen!"));
    }

    public List<UserOutDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserDirector::EntityToUserOutDTO)
                .toList();
    }

    public User changeRole(String id, String roleIdentifier) throws UserNotFoundException {
        StandardRoles role = StandardRoles.fromIdentifier(roleIdentifier);
        User user = userRepository.findFirstByOuterId(id).orElseThrow(UserNotFoundException::new);
        if (!user.getRole().equals(role)) {
            user.setRole(role);
        }
        return userRepository.saveAndFlush(user);
    }

    public User lockUser(String id) throws UserNotFoundException {
        User user = userRepository.findFirstByOuterId(id).orElseThrow(UserNotFoundException::new);
        user.setLocked(!user.isLocked());
        return userRepository.saveAndFlush(user);
    }

    private void tryToSetUniqueOuterId(User user) {
        String candidateId = RandomStringGenerator.nextOuterId(Constants.USER_OUTER_ID_LENGTH);
        Optional<User> userOptional = userRepository.findFirstByOuterId(candidateId);
        if (userOptional.isPresent()) {
            System.out.println("Collision on ID generation [User]! Must roll again.");
            tryToSetUniqueOuterId(user);
            return;
        }
        user.setOuterId(candidateId);
    }
}
