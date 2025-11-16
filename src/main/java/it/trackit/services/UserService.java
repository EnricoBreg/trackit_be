package it.trackit.services;

import it.trackit.commons.exceptions.UserNotFoundException;
import it.trackit.dto.RegisterUserRequest;
import it.trackit.dto.UpdateUserRequest;
import it.trackit.dto.UserDto;
import it.trackit.entities.User;
import it.trackit.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public List<UserDto> getAllUsers() {
    var users = userRepository.findAll();
    return users.stream().map(user ->
      UserDto.builder()
              .nome(user.getNome())
              .cognome(user.getCognome())
              .email(user.getEmail())
              .username(user.getUsername())
              .build()
    ).toList();
  }

  public UserDto getUser(Long id) {
    var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    return UserDto.builder()
            .id(user.getId())
            .nome(user.getNome())
            .cognome(user.getCognome())
            .email(user.getEmail())
            .username(user.getUsername())
            .build();
  }

  public UserDto registerUser(RegisterUserRequest request) {
    var newUser = User.builder()
            .username(request.getUsername())
            .email(request.getEmail())
            .password(request.getPassword())
            .isActive(true)
            .isSuperAdmin(false)
            .build();

    userRepository.save(newUser);

    return UserDto.builder()
            .id(newUser.getId())
            .nome(newUser.getNome())
            .cognome(newUser.getCognome())
            .username(newUser.getUsername())
            .email(newUser.getEmail())
            .build();
  }

  public UserDto updateUser(Long userId, UpdateUserRequest request) {
    var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

    if (request.getUsername() != null)
      user.setUsername(request.getUsername());
    if (request.getEmail() != null)
      user.setEmail(request.getEmail());
    if (request.getNome() != null)
      user.setNome(request.getNome());
    if (request.getCognome() != null)
      user.setCognome(request.getCognome());

    userRepository.save(user);

    return UserDto.builder()
            .id(user.getId())
            .nome(user.getNome())
            .cognome(user.getCognome())
            .email(user.getEmail())
            .username(user.getUsername())
            .build();
  }

  public void deleteUser(Long userId) {
    var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    userRepository.delete(user);
  }
}
