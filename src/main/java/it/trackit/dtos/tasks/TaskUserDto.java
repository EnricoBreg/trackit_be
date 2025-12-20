package it.trackit.dtos.tasks;

import lombok.Data;

@Data
public class TaskUserDto {
  private Long id;
  private String username;
  private String nominativo;
  private String email;
}
