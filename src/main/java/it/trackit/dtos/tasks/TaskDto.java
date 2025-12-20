package it.trackit.dtos.tasks;

import it.trackit.entities.Task;
import lombok.Data;

@Data
public class TaskDto {
  private Long id;
  private String nome;
  private String descrizione;
  private Task.Stato stato;
  private Integer priorita;
  private TaskUserDto assegnatario;
  private TaskUserDto creatore;
}
