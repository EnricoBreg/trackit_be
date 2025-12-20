package it.trackit.dtos.projects;

import lombok.Data;

@Data
public class TaskUserDto {
  private Long id;
  private String username;
  private String nominativo;
  private String email;
}
