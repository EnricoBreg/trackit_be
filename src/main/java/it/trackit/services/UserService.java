package it.trackit.services;

import it.trackit.commons.exceptions.UserExistsException;
import it.trackit.commons.exceptions.UserNotFoundException;
import it.trackit.commons.utils.DomainUtils;
import it.trackit.config.security.UserPrincipal;
import it.trackit.config.security.permissions.global.GlobalPermissionResolver;
import it.trackit.config.security.permissions.project.ProjectPermission;
import it.trackit.dtos.*;
import it.trackit.entities.User;
import it.trackit.mappers.UserMapper;
import it.trackit.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  private final AuthService authService;

  public PaginatedResponse<UserDto> getAllUsers(Pageable pageable, String searchText) {
    Page<User> page = searchText != null ? userRepository.searchUsers(searchText, pageable)
                                         : userRepository.findAll(pageable);
    var isSuperAdmin = authService.isCurrentUserASuperAdmin();
    List<UserDto> filteredUsers = new ArrayList<UserDto>();
    if (!isSuperAdmin) {
      filteredUsers = page.getContent().stream().filter(u -> !u.isSuperAdmin()).map(userMapper::toDto).toList();
    } else {
      filteredUsers = page.getContent().stream().map(userMapper::toDto).toList();
    }

    return DomainUtils.buildPaginatedResponse(page, filteredUsers);
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
    var user = userRepository.findByUsername(request.getUsername()).orElse(null);
    if (user != null) {
      throw new UserExistsException("User already exists", Map.of(
        "username", request.getUsername() + " already exists"
      ));
    }
    user = userRepository.findByEmail(request.getEmail()).orElse(null);
    if (user != null) {
      throw new UserExistsException("User already exists", Map.of(
        "email", request.getEmail() + " already exists"
      ));
    }

    var newUser = userMapper.toEntity(request);
    newUser.setActive(true);
    // newUser.setGlobalRole(GlobalRole.ROLE_USER);
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
    UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return userMapper.toDto(principal.getUser());
  }

  public void changeUserPassword(Long userId, String newPassword) {
    var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    user.setPassword(passwordEncoder.encode(newPassword.trim()));
    userRepository.save(user);
  }

  public UserDetailsDto buildUserDetailsDto(User user) {
    var userDto = userMapper.toDto(user);
    var globalPermissions = GlobalPermissionResolver.forRole(user.getGlobalRole());
    //var projectPermission = ProjectPermissionResolver.resolve(...)
    Set<ProjectPermission> projectPermissions = Set.of();

    return UserDetailsDto.builder()
      .user(userDto)
      .globalPermissions(globalPermissions)
      .projectPermissions(projectPermissions)
      .build();
  }
}
