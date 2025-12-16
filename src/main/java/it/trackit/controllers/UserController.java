package it.trackit.controllers;

import it.trackit.dtos.RegisterUserRequest;
import it.trackit.dtos.UpdateUserRequest;
import it.trackit.dtos.UserDto;
import it.trackit.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @GetMapping
  public List<UserDto> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/{id}")
  public UserDto getUser(@PathVariable("id") Long userId) {
    return userService.getUser(userId);
  }

  @PostMapping
  public ResponseEntity<?> registerUser(
      @Valid @RequestBody RegisterUserRequest request,
      UriComponentsBuilder uriBuilder
  ) {
    var userDto = userService.registerUser(request);
    var uri = uriBuilder.path("/user/{id}").buildAndExpand(userDto.getId()).toUri();
    return ResponseEntity.created(uri).body(userDto);
  }

  @PutMapping("/{id}")
  public UserDto updateUser(
          @PathVariable("id") Long userId,
          @RequestBody UpdateUserRequest request
  ) {
    return userService.updateUser(userId, request);
  }

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable("id") Long id) {
    userService.deleteUser(id);
  }
}
