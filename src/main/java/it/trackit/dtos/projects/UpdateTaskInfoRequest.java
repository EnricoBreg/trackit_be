package it.trackit.dtos.projects;

import it.trackit.entities.Task;
import lombok.Data;

@Data
public class UpdateTaskInfoRequest {
  private String titolo;
  private String descrizione;
  private Task.Stato stato;
  private Integer priorita;
}
