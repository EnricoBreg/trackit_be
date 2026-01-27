package it.trackit.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
  @NotBlank(message = "login.usernameRequired")
  private String username;

  @NotBlank(message = "login.passwordRequired")
  private String password;
}
