package it.trackit.controllers;

import it.trackit.dto.RegisterUserRequest;
import it.trackit.dto.UpdateUserRequest;
import it.trackit.services.UserService;
import it.trackit.entities.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @GetMapping
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/{id}")
  public User getUser(@PathVariable("id") Long userId) {
    return userService.getUser(userId);
  }

  @PostMapping
  public ResponseEntity<?> registerUser(
      @Valid @RequestBody RegisterUserRequest request,
      UriComponentsBuilder uriBuilder
  ) {
    var user = userService.registerUser(request);
    var uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
    return ResponseEntity.created(uri).body(user);
  }

  @PutMapping("/{id}")
  public User updateUser(
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
