package it.trackit.dtos.projects;

import it.trackit.dtos.UserDto;
import lombok.Data;

@Data
public class ProjectMemberDto {
  private UserDto user;
  private String role;
}
