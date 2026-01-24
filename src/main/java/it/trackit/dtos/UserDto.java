package it.trackit.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
  private Long id;
  private String nome;
  private String cognome;
  private String nominativo;
  private String username;
  private String email;
  private Boolean active;
}
