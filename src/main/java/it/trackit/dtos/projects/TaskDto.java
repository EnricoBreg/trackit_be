package it.trackit.dtos.projects;

import it.trackit.entities.Task;
import lombok.Data;

import java.util.UUID;

@Data
public class TaskDto {
  private Long id;
  private String titolo;
  private String descrizione;
  private Task.Stato stato;
  private Integer priorita;
  private Integer progresso;
  private TaskUserDto assegnatario;
  private TaskUserDto creatore;
  private UUID projectId;
}
