package it.trackit.dtos.projects;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddProjectMemberRequest {
  @NotNull(message = "user.idNotNull")
  private Long userId;

  @NotNull(message = "role.notNull")
  private String role;
}
