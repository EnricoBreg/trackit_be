package it.trackit.controllers;

import it.trackit.dto.LoginRequest;
import it.trackit.repositories.UserRepository;
import it.trackit.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  @PostMapping("/login")
  public ResponseEntity<?> login(
    @Valid @RequestBody LoginRequest request
  ) {
    var user = userRepository.findByUsername(request.getUsername()).orElse(null);
    if (user == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    return ResponseEntity.ok().build();
  }
}
