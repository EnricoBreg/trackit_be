package it.trackit.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequest {
    @Size(max = 255, message = "The first name must be max 255 characters")
    private String nome;

    @Size(max = 255, message = "The last name must be max 255 characters")
    private String cognome;

    @NotBlank(message = "Username is required")
    @Size(max = 255, message = "The username must be max 255 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 25, message = "Password must be between 6 and 25 characters")
    private String password;
}
