package it.trackit.config.security;

import it.trackit.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("userSecurityRules")
@RequiredArgsConstructor
public class UserSecurityRules extends BaseAppSecurityRules {

  private final UserRepository userRepository;

  public boolean isCurrentAuthenticatedUser(Long userId) {
    if (isCurrentSuperAdmin()) return true;
    Long currentAuthenticatedUserId = getAuthenticatedUserId();
    return currentAuthenticatedUserId.equals(userId);
  }
}
