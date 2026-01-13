package it.trackit.config.security.permissions.global;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class GlobalPermissionResolver {

  public static Set<GlobalPermission> forRole(GlobalRole role) {
    return switch (role) {
      case ROLE_SUPER_ADMIN -> Set.of(GlobalPermission.values());
      case ROLE_ADMIN -> Set.of(GlobalPermission.USER_RESET_PASSWORD, GlobalPermission.USER_ENABLE_DISABLE);
      case ROLE_USER -> Set.of();
    };
  }
}
