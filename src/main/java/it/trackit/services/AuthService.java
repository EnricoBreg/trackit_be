package it.trackit.services;

import it.trackit.config.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private static final String SUPER_ADMIN_USERNAME = "super";
  private static final String SUPER_ADMIN_GLOBAL_ROLE = "ROLE_SUPER_ADMIN";

  /**
   * Metodo per verificare se l'utente attualmente loggato è un super admin o meno.
   * Il risultato viene calcolato in base all'authorities impostata duranete il
   * login dell'utente. Nome authorities: ROLE_SUPER_ADMIN.
   * @return true o false
   */
  public boolean isCurrentUserASuperAdmin() {
    UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal == null) {
      return false;
    }
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getAuthorities().stream()
      .anyMatch(a -> a.getAuthority().equals(SUPER_ADMIN_GLOBAL_ROLE));
  }
}
