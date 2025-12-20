package it.trackit.dtos.projects;

import it.trackit.entities.Project.Stato;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class ProjectDto {
  private UUID id;
  private String nome;
  private String descrizione;
  private Stato stato;
  private LocalDateTime dataCreazione;
  private LocalDateTime dataUltimaModifica;
  private LocalDateTime dataChiusura;
  private Integer tasksCount;
  private List<TaskDto> tasks = new ArrayList<>();
}
