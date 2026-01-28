package it.trackit.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "tasks")
@Getter
@Setter
public class Task {
  public enum Stato {
    DA_ASSEGNARE,
    ASSEGNATO,
    IN_LAVORAZIONE,
    COMPLETATA,
    STAND_BY,
    BLOCCATO,
    ANNULLATO,
  }

  public enum Priorita {
    BASSA, MEDIO_BASSA, MEDIA, MEDIO_ALTA, ALTA
  }

  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  private Long id;

  @Column(name = "titolo", nullable = false)
  private String titolo;

  @Column(name = "descrizione", nullable = false)
  private String descrizione;

  @Enumerated(EnumType.STRING)
  @Column(name = "stato", nullable = false)
  private Stato stato = Stato.DA_ASSEGNARE;

  @Enumerated(EnumType.STRING)
  @Column(name = "priorita", nullable = false)
  private Priorita priorita = Priorita.MEDIA;

  @Min(0)
  @Max(100)
  @Column(name = "progresso")
  private Integer progresso = 0;

  @Column(name = "data_creazione", nullable = false)
  private LocalDateTime dataCreazione = LocalDateTime.now();

  @Column(name = "data_assegnazione")
  private LocalDateTime dataAssegnazione;

  @Column(name = "data_inizio_lavorazione")
  private LocalDateTime dataInizioLavorazione;

  @Column(name = "data_ultima_modifica", nullable = false)
  private LocalDateTime dataUltimaModifica = LocalDateTime.now();

  @Column(name = "data_scadenza", nullable = false)
  private LocalDateTime dataScadenza;

  @Column(name = "data_chiusura")
  private LocalDateTime dataChiusura;

  @ManyToOne
  @JoinColumn(name = "project_id", nullable = false)
  private Project project;

  @ManyToOne
  @JoinColumn(name = "assignee_id")
  private User assegnatario;

  @ManyToOne
  @JoinColumn(name = "reporter_id", nullable = false)
  private User reporter;

  @ManyToOne
  @JoinColumn(name = "parent_task_id")
  private Task parentTask;

  @OneToMany(mappedBy = "parentTask", cascade = CascadeType.MERGE,
             fetch = FetchType.LAZY, orphanRemoval = true)
  private List<Task> subTasks = new ArrayList<>();

  @PreUpdate
  private void onPreUpdate() {
    dataUltimaModifica = LocalDateTime.now();
  }

  public void addSubTask(Task subTask) {
    if (subTasks.contains(subTask)) return;
    subTasks.add(subTask);
    subTask.setParentTask(this);
  }

  public void removeSubTask(Task subTask) {
    if (!subTasks.contains(subTask)) return;
    subTasks.remove(subTask);
    subTask.setParentTask(null);
  }

  public boolean isChiusa() {
    return this.stato == Stato.COMPLETATA || this.stato == Stato.ANNULLATO;
  }

  public boolean isChiusaInRitardo() {
    return dataChiusura != null
           && dataScadenza != null
           && dataChiusura.isAfter(dataScadenza);
  }
}
