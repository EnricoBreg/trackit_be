package it.trackit.config.security;

import it.trackit.services.AuthService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class BaseAppSecurityRules {

  private final AuthService authService;

  protected Long getAuthenticatedUserId() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
    return principal.getUser().getId();
  }

  protected boolean isCurrentSuperAdmin() {
    return authService != null && authService.isCurrentUserASuperAdmin();
  }

  protected boolean isCurrentAdmin() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getAuthorities().stream()
      .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
  }
}
