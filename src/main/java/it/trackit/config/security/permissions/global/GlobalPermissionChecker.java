package it.trackit.config.security.permissions.global;

import it.trackit.entities.User;
import it.trackit.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("globalPermissionChecker")
@AllArgsConstructor
public class GlobalPermissionChecker {

  private final UserRepository userRepository;

  public boolean hasPermission(GlobalPermission permission) {
    var userId = (User) SecurityContextHolder
      .getContext()
      .getAuthentication()
      .getPrincipal();

    var user = userRepository.findById(userId.getId()).orElse(null);
    if (user == null) return false;

    return GlobalPermissionResolver.forRole(user.getGlobalRole()).contains(permission);
  }

  public boolean hasAnyPermission(GlobalPermission... permissions) {
    var permissionList = List.of(permissions);
    return permissionList.stream().anyMatch(this::hasPermission);
  }

  public boolean canResetPassword() {
    return hasPermission(GlobalPermission.USER_RESET_PASSWORD);
  }
}
