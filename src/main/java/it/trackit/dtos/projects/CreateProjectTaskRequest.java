package it.trackit.dtos.projects;

import it.trackit.commons.utils.ValueOfEnum;
import it.trackit.entities.Task;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateProjectTaskRequest {
  @NotBlank(message = "Titolo is required")
  @Size(max = 255, message = "The title must be max 255 characters")
  private String titolo;

  @NotBlank(message = "Descrizione is required")
  @Size(max = 1000, message = "The description must be max 1000 characters")
  private String descrizione;

  @NotNull(message = "Stato is required")
  @ValueOfEnum(enumClass = Task.Stato.class, message = "Stato has not a valid value")
  private String stato;

  @NotNull(message = "Priorita is required")
  @Min(value = 1, message = "The priority must be greater or equal to 1")
  @Max(value = 5, message = "The priority must be less or equal to 5")
  private Integer priorita;

  @NotNull(message = "Utente assegnatario is required")
  private Long assegnatario;
}
