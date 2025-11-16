package it.trackit.services;

import it.trackit.dto.RegisterUserRequest;
import it.trackit.entities.User;
import it.trackit.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User getUser(Long id) {
    return userRepository.findById(id).orElse(null);
  }

  public User registerUser(RegisterUserRequest request) {
    var newUser = User.builder()
            .username(request.getUsername())
            .email(request.getEmail())
            .password(request.getPassword())
            .isActive(true)
            .isSuperAdmin(false)
            .build();

    userRepository.save(newUser);

    return newUser;
  }
}
