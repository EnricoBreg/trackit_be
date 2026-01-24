package it.trackit.config.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class BaseAppSecurityRules {

  protected Long getAuthenticatedUserId() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
    return principal.getUser().getId();
  }

  protected boolean isCurrentSuperAdmin() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getAuthorities().stream()
      .anyMatch(a -> a.getAuthority().equals("ROLE_SUPER_ADMIN"));
  }

  protected boolean isCurrentAdmin() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getAuthorities().stream()
      .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
  }
}
