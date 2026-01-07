package it.trackit.services;

import it.trackit.commons.exceptions.UserNotFoundException;
import it.trackit.commons.utils.DomainUtils;
import it.trackit.dtos.PaginatedResponse;
import it.trackit.dtos.RegisterUserRequest;
import it.trackit.dtos.UpdateUserRequest;
import it.trackit.dtos.UserDto;
import it.trackit.entities.User;
import it.trackit.mappers.UserMapper;
import it.trackit.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  public PaginatedResponse<UserDto> getAllUsers(Pageable pageable) {
    Page<User> page = userRepository.findAll(pageable);
    var users = page.getContent().stream().map(userMapper::toDto).toList();
    return DomainUtils.buildPaginatedResponse(page, users);
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
    userMapper.update(request, user);

    userRepository.save(user);

    return userMapper.toDto(user);
  }

  public void deleteUser(Long userId) {
    var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    userRepository.delete(user);
  }

  public UserDto getCurrentAuthenticatedUser() {
    String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    return getUser(Long.valueOf(userId));
  }

  public void save(User user) {
    userRepository.save(user);
  }
}
