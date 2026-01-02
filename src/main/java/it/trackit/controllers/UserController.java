package it.trackit.controllers;

import it.trackit.commons.exceptions.UserNotFoundException;
import it.trackit.dtos.RegisterUserRequest;
import it.trackit.dtos.UpdateUserRequest;
import it.trackit.dtos.UserDto;
import it.trackit.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @GetMapping
  @PreAuthorize("isAuthenticated()")
  public List<UserDto> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/{id}")
  @PreAuthorize("@userSecurityRules.isCurrentAuthenticatedUser(#userId)")
  public UserDto getUser(@PathVariable("id") Long userId) {
    return userService.getUser(userId);
  }

  @GetMapping("/me")
  @PreAuthorize("isAuthenticated()")
  public UserDto me() {
    return userService.getCurrentAuthenticatedUser();
  }

  @PostMapping
  @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
  public ResponseEntity<?> registerUser(
      @Valid @RequestBody RegisterUserRequest request,
      UriComponentsBuilder uriBuilder
  ) {
    var userDto = userService.registerUser(request);
    var uri = uriBuilder.path("/user/{id}").buildAndExpand(userDto.getId()).toUri();
    return ResponseEntity.created(uri).body(userDto);
  }

  @PutMapping("/{id}")
  @PreAuthorize("@userSecurityRules.isCurrentAuthenticatedUser(#userId)")
  public UserDto updateUser(
          @PathVariable("id") Long userId,
          @RequestBody UpdateUserRequest request
  ) {
    return userService.updateUser(userId, request);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("@hasRole('ROLE_SUPER_ADMIN')")
  public void deleteUser(@PathVariable("id") Long id) {
    userService.deleteUser(id);
  }

  @ExceptionHandler({UserNotFoundException.class})
  public ResponseEntity<Map<String, String>> handleUserNotFound() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found."));
  }
}
