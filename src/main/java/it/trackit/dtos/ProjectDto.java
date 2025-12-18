package it.trackit.dtos;

import it.trackit.entities.Project.Stato;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ProjectDto {
  private UUID id;
  private String nome;
  private String descrizione;
  private Stato stato;
  private LocalDateTime dataCreazione;
  private LocalDateTime dataUltimaModifica;
  private LocalDateTime dataChiusura;
}
