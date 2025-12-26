package it.trackit.dtos.projects;

import it.trackit.commons.utils.ValueOfEnum;
import it.trackit.entities.Task;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateTaskInfoRequest {
  @NotBlank(message = "Titolo is required")
  @Size(min = 5, max = 255, message = "The title must be min 5 and 255 max characters")
  private String titolo;

  @Size(max = 1000, message = "The description must be max 1000 characters")
  private String descrizione;

  @NotNull(message = "Stato is required")
  @ValueOfEnum(enumClass = Task.Stato.class, message = "Stato has not a valid value")
  private String stato;

  @NotNull(message = "Priorita is required")
  @Min(message = "Priorita must be greater or equal to 1", value = 1)
  @Max(message = "Priorita must be less or equal to 5", value = 5)
  private Integer priorita;
}
