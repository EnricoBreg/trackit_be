package it.trackit.dtos.projects;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddProjectMemberRequest {
  @NotNull(message = "User ID non può essere null")
  private Long userId;

  @NotNull(message = "role must not be null")
  private String role;
}
