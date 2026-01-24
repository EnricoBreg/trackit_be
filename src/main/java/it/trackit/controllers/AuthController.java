package it.trackit.controllers;

import it.trackit.config.JwtConfig;
import it.trackit.dtos.LoginRequest;
import it.trackit.dtos.LoginResponse;
import it.trackit.dtos.UserDetailsDto;
import it.trackit.mappers.UserMapper;
import it.trackit.repositories.UserRepository;
import it.trackit.services.JwtService;
import it.trackit.services.UserService;
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
  private final JwtConfig jwtConfig;
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final UserService userService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(
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

    var userDetails = userService.buildUserDetailsDto(user);

    var cookie = new Cookie("refresh-token", refreshToken);
    cookie.setHttpOnly(true);
    cookie.setPath("/api/auth/refresh");
    cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration()); // 7 days
    cookie.setSecure(true);
    cookie.setAttribute("SameSite", "None");
    response.addCookie(cookie);

    return ResponseEntity.ok(new LoginResponse(accessToken, userDetails));
  }

  @PostMapping("/refresh")
  public ResponseEntity<LoginResponse> refresh(
    @CookieValue("refresh-token") String refreshToken
  ) {
    if (!jwtService.validateToken(refreshToken)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    var userId = jwtService.getUserIdFromToken(refreshToken);
    var user = userRepository.findById(userId).orElseThrow();
    var accessToken = jwtService.generateAccessToken(user);

    var userDetails = userService.buildUserDetailsDto(user);

    return ResponseEntity.ok(new LoginResponse(accessToken, userDetails));
  }

  @GetMapping("/me")
  public ResponseEntity<UserDetailsDto> me() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    var userId = (Long) authentication.getPrincipal();

    var user = userRepository.findById(userId).orElse(null);
    if (user == null) {
      return ResponseEntity.notFound().build();
    }

    var userDetails = userService.buildUserDetailsDto(user);

    return ResponseEntity.ok(userDetails);
  }

  @ExceptionHandler({BadCredentialsException.class})
  public ResponseEntity<Void> handleBadCredentialsException() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }
}
