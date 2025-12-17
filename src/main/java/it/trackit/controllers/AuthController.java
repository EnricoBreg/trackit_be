package it.trackit.controllers;

import it.trackit.dtos.JwtResponse;
import it.trackit.dtos.LoginRequest;
import it.trackit.services.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  @PostMapping("/login")
  public ResponseEntity<JwtResponse> login(
    @Valid @RequestBody LoginRequest request
  ) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        request.getUsername(),
        request.getPassword()
      )
    );

    var token = jwtService.generateToken(request.getUsername());

    return ResponseEntity.ok(new JwtResponse(token));
  }

  @PostMapping("/validate")
  public Boolean validate(@RequestHeader("Authorization") String authHeader) {
    var token = authHeader.replace("Bearer ", "");

    return jwtService.validateToken(token);
  }

  @ExceptionHandler({BadCredentialsException.class})
  public ResponseEntity<Void> handleBadCredentialsException() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }
}
