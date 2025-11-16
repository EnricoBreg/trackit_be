package it.trackit.dto;

import lombok.Data;

@Data
public class UpdateUserRequest {
  private String nome;
  private String cognome;
  private String email;
  private String username;
}
