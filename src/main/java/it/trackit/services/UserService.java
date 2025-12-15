package it.trackit.services;

import it.trackit.commons.exceptions.UserNotFoundException;
import it.trackit.dto.RegisterUserRequest;
import it.trackit.dto.UpdateUserRequest;
import it.trackit.dto.UserDto;
import it.trackit.entities.User;
import it.trackit.mappers.UserMapper;
import it.trackit.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  public List<UserDto> getAllUsers() {
    var users = userRepository.findAll();
    return users.stream().map(userMapper::toDto).toList();
  }

  public UserDto getUser(Long id) {
    var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    return userMapper.toDto(user);
  }

  /**
   * Metodo per registrare un utente da un request @{link {@link RegisterUserRequest}}
   * @param request di tipo RegisterUserRequest
   * @return dto rappresentante l'utente registrato
   */
  public UserDto registerUser(RegisterUserRequest request) {
    var newUser = userMapper.toEntity(request);
    newUser.setIsActive(true);
    newUser.setIsSuperAdmin(false);
    newUser.setPassword(passwordEncoder.encode(request.getPassword()));

    userRepository.save(newUser);

    return userMapper.toDto(newUser);
  }

  public UserDto updateUser(Long userId, UpdateUserRequest request) {
    var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

//    if (request.getUsername() != null)
//      user.setUsername(request.getUsername());
//    if (request.getEmail() != null)
//      user.setEmail(request.getEmail());
//    if (request.getNome() != null)
//      user.setNome(request.getNome());
//    if (request.getCognome() != null)
//      user.setCognome(request.getCognome());

    userMapper.update(request, user);

    userRepository.save(user);

    return userMapper.toDto(user);
  }

  public void deleteUser(Long userId) {
    var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    userRepository.delete(user);
  }

  public void save(User user) {
  }
}
