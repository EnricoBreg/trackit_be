package it.trackit.config.security;

import it.trackit.repositories.ProjectMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component("projectSecurityRules")
@RequiredArgsConstructor
public class ProjectSecurityRules extends BaseAppSecurityRules {

  // private final UserRepository userRepository;
  private final ProjectMemberRepository projectMemberRepository;

  @Transactional(readOnly = true)
  public boolean isMember(UUID projectId) {
    // Controllo se l'utente è super-admin dal SecurityContext
    if (isCurrentSuperAdmin()) return true;

    // Controllo sulla membership di un utente nel progetto
    Long userId = getAuthenticatedUserId();
    return projectMemberRepository.existsByProject_IdAndUser_Id(projectId, userId);
  }

  @Transactional(readOnly = true)
  public boolean isManager(UUID projectId) {
    if (isCurrentSuperAdmin()) return true;

    Long userId = getAuthenticatedUserId();
    return projectMemberRepository.findByProject_IdAndUserId(projectId, userId)
      .map(member -> "MANAGER".equals(member.getRole().getNome()))
      .orElse(false);
  }
}
