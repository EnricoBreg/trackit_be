package it.trackit.services;

import it.trackit.commons.exceptions.ForbiddenException;
import it.trackit.config.security.permissions.global.GlobalRole;
import it.trackit.config.security.permissions.project.PermissionMatrix;
import it.trackit.config.security.permissions.project.ProjectPermission;
import it.trackit.entities.Project;
import it.trackit.entities.ProjectMember;
import it.trackit.entities.User;
import it.trackit.repositories.ProjectMemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserPermissionService {

  private final ProjectMemberRepository projectMemberRepository;

  public Set<ProjectPermission> forUserInProject(User user, Project project) {
    ProjectMember projectMember = projectMemberRepository
      .findByProject_IdAndUserId(project.getId(), user.getId())
      .orElseThrow(ForbiddenException::new);

    return resolvePermissions(user, projectMember);
  }

  private Set<ProjectPermission> resolvePermissions(User user, ProjectMember projectMember) {
    if (user.getGlobalRole() == GlobalRole.ROLE_SUPER_ADMIN) {
      return EnumSet.allOf(ProjectPermission.class);
    }

    return PermissionMatrix.getPermissionsForLevel(projectMember.getRole().getLivello());
  }
}
