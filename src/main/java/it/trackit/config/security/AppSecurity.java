package it.trackit.config.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class AppSecurity {

  protected Long getAuthenticatedUserId() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    var principal = authentication.getPrincipal();
    if (principal instanceof Number) {
      return ((Number) principal).longValue();
    }
    return Long.parseLong(principal.toString());
  }

  protected boolean isCurrentSuperAdmin() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getAuthorities().stream()
      .anyMatch(a -> a.getAuthority().equals("ROLE_SUPER_ADMIN"));
  }
}
