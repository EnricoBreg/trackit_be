package it.trackit.dtos.projects;

import it.trackit.commons.utils.ValueOfEnum;
import it.trackit.entities.Task;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateProjectTaskRequest {
  @NotBlank(message = "task.titolo.richiesto")
  @Size(max = 255, message = "task.titolo.troppoLungo")
  private String titolo;

  @NotBlank(message = "task.descrizione.richiesta")
  @Size(max = 1000, message = "task.descrizione.troppoLunga")
  private String descrizione;

  @NotNull(message = "task.stato.richiesto")
  @ValueOfEnum(enumClass = Task.Stato.class, message = "task.stato.valoreNonValido")
  private String stato;

  @NotNull(message = "task.priorita.richiesta")
  @Min(value = 1, message = "task.priorita.valoreMinimoNonValido")
  @Max(value = 5, message = "task.priorita.valoreMassimoNonValido")
  private Integer priorita;

  @NotNull(message = "task.utenteAssegnatario.richiesto")
  private Long assegnatario;
}
