package it.trackit.controllers;

import it.trackit.dtos.JwtResponse;
import it.trackit.dtos.LoginRequest;
import it.trackit.dtos.UserDto;
import it.trackit.mappers.UserMapper;
import it.trackit.repositories.UserRepository;
import it.trackit.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @PostMapping("/login")
  public ResponseEntity<JwtResponse> login(
    @Valid @RequestBody LoginRequest request,
    HttpServletResponse response
  ) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        request.getUsername(),
        request.getPassword()
      )
    );

    var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
    var accessToken = jwtService.generateAccessToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);

    var cookie = new Cookie("refresh-token", refreshToken);
    cookie.setHttpOnly(true);
    cookie.setPath("/api/auth/refresh");
    cookie.setMaxAge(604800); // 7 days
    cookie.setSecure(true);
    response.addCookie(cookie);

    return ResponseEntity.ok(new JwtResponse(accessToken));
  }

  @PostMapping("/validate")
  public Boolean validate(@RequestHeader("Authorization") String authHeader) {
    var token = authHeader.replace("Bearer ", "");

    return jwtService.validateToken(token);
  }

  @GetMapping("/me")
  public ResponseEntity<UserDto> me() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    var userId = (Long)authentication.getPrincipal();

    var user = userRepository.findById(userId).orElse(null);
    if (user == null) {
      return ResponseEntity.notFound().build();
    }

    var userDto = userMapper.toDto(user);

    return ResponseEntity.ok(userDto);
  }

  @ExceptionHandler({BadCredentialsException.class})
  public ResponseEntity<Void> handleBadCredentialsException() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }
}
