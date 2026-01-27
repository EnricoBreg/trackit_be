package it.trackit.dtos.projects;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignTaskRequest {
  @NotNull(message = "task.assegnatarioRichiesto")
  private Long userId;
}
