package it.trackit.controllers;

import it.trackit.dto.UserLoginRequest;
import it.trackit.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

  private final UserService userService;
  //private final AuthController authController;

  @PostMapping("/login")
  public String login(
    @RequestBody UserLoginRequest request
  ) {
    System.out.println("Request received for login");
    System.out.println("Username: " + request.getUsername());
    System.out.println("Password: " + request.getPassword());
    return "Login successful!";
  }

  @GetMapping("/hello")
  public String hello(
    @RequestBody UserLoginRequest request
  ) {
    return "Hello World!";
  }
}
