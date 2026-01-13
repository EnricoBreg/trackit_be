package it.trackit.config.security.permissions.project;

import it.trackit.entities.ProjectMember;
import it.trackit.entities.User;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ProjectPermissionResolver {

  public Set<ProjectPermission> resolve(
    User user,
    ProjectMember membership
  ) {
    if (user.isSuperAdmin()) {
      return Set.of(ProjectPermission.values());
    }
    return PermissionMatrix.getPermissionsForLevel(membership.getRole().getLivello());
  }
}
