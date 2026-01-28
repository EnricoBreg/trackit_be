package it.trackit.dtos.projects;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateProjectRequest {
  private String nome;
  private String descrizione;
  private String stato;
  private LocalDateTime dataCreazione;
  private LocalDateTime dataInizioLavorazione;
  private LocalDateTime dataUltimaModifica;
  private LocalDateTime dataScadenza;
  private LocalDateTime dataPrevistaChiusura;
  private LocalDateTime dataChiusura;
}
