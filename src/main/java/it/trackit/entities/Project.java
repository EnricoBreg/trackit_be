package it.trackit.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "projects")
@Getter
@Setter
public class Project {
  public enum Stato {
    PENDING,
    IN_PROGRESS,
    DONE,
    CANCELLED,
    REJECTED,
    ARCHIVED
  }

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "uuid")
  private UUID id;

  @Column(name = "nome_progetto")
  private String nome;

  @Column(name = "descrizione")
  private String descrizione;

  @Enumerated(EnumType.STRING)
  @Column(name = "stato")
  private Stato stato;

  @Column(name = "created_at")
  private LocalDateTime dataCreazione;

  @Column(name = "updated_at")
  private LocalDateTime dataUltimaModifica;

  @Column(name = "ended_at")
  private LocalDateTime dataChiusura;
}
