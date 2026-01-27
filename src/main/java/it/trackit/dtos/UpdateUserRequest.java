package it.trackit.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {
  @Size(max = 255, message = "user.nome.lunghezzaMassima")
  private String nome;

  @Size(max = 255, message = "user.cognome.lunghezzaMassima")
  private String cognome;

  @NotBlank(message = "user.email.richiesta")
  @Email(message = "user.email.nonValida")
  private String email;

  @NotBlank(message = "user.username.richiesto")
  @Size(max = 255, message = "user.username.lunghezzaMassima")
  private String username;
}
