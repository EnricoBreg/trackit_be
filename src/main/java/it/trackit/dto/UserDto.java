package it.trackit.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
  private Long id;
  private String nome;
  private String cognome;
  private String username;
  private String email;
}
