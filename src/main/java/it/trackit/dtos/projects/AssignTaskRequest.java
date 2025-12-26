package it.trackit.dtos.projects;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignTaskRequest {
  @NotNull(message = "Assignee ID is required")
  private Long userId;
}
