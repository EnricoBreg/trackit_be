package it.trackit.dtos;

import it.trackit.config.security.permissions.global.GlobalPermission;
import it.trackit.config.security.permissions.project.ProjectPermission;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserDetailsDto {
  private UserDto user;
  private Set<GlobalPermission> globalPermissions;
  private Set<ProjectPermission> projectPermissions;
}
