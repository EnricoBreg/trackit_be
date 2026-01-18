package it.trackit.config.security.permissions.global;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class GlobalPermissionResolver {

  public static Set<GlobalPermission> forRole(GlobalRole role) {
    return switch (role) {
      case ROLE_SUPER_ADMIN -> Set.of(GlobalPermission.values());
      case ROLE_ADMIN -> getPermissionForAdmin();
      case ROLE_USER -> getPermissionForUser();
    };
  }

  private static Set<GlobalPermission> getPermissionForAdmin() {
    return Set.of(
      GlobalPermission.USER_RESET_PASSWORD,
      GlobalPermission.USER_ENABLE_DISABLE,
      GlobalPermission.USER_CREATE,
      GlobalPermission.USER_EDIT,
      GlobalPermission.USER_DELETE,

      GlobalPermission.PROJECT_CREATE,
      GlobalPermission.PROJECT_EDIT
      );
  };

  private static Set<GlobalPermission> getPermissionForUser() {
    return Set.of();
  };
}
