package it.trackit.dtos;

import it.trackit.config.security.permissions.global.GlobalPermission;
import it.trackit.config.security.permissions.project.ProjectPermission;
import lombok.Data;

import java.util.Set;

@Data
public class UserDetailResponse {
  private UserDto user;
  private Set<GlobalPermission> globalPermissions;
  private Set<ProjectPermission> projectPermissions;
}
