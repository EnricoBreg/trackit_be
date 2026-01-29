package it.trackit.dtos.projects;

import it.trackit.commons.utils.ValueOfEnum;
import it.trackit.entities.Task;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

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
  @ValueOfEnum(enumClass = Task.Priorita.class, message = "task.priorita.valoreNonValido")
  private String priorita;

  @Min(value = 0, message = "task.progresso.valoreMinimoNonValido")
  @Max(value = 100, message = "task.progresso.valoreMassimoNonValido")
  private Integer progresso;

  @NotNull(message = "task.dataCreazione.richiesta")
  private LocalDateTime dataCreazione;

  private LocalDateTime dataAssegnazione;

  private LocalDateTime dataInizioLavorazione;

  @NotNull(message = "task.dataScadenza.richiesta")
  private LocalDateTime dataScadenza;

  private LocalDateTime dataChiusura;

  //@NotNull(message = "task.assegnatario.richiesto")
  private Long assegnatario;
}
