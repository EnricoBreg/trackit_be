package it.trackit.dtos.projects;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TaskDto {
  private Long id;
  private String titolo;
  private String descrizione;
  private String stato;
  private String priorita;
  private Integer progresso;
  private LocalDateTime dataCreazione;
  private LocalDateTime dataAssegnazione;
  private LocalDateTime dataInizioLavorazione;
  private LocalDateTime dataUltimaModifica;
  private LocalDateTime dataScadenza;
  private LocalDateTime dataChiusura;
  private TaskUserDto assegnatario;
  private TaskUserDto reporter;
  private UUID projectId;
}
