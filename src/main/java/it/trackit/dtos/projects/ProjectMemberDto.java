package it.trackit.dtos.projects;

import it.trackit.dtos.UserDto;
import lombok.Data;

import java.util.UUID;

@Data
public class ProjectMemberDto {
  private UserDto user;
  private String role;
  private UUID projectId;
}
