package it.trackit.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {
  @NotBlank(message = "user.password.richiesta")
  @Size(min = 8, message = "user.password.lunghezzaMinima")
  private String newPassword;
}
