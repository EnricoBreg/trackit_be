package it.trackit.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "projects")
@Getter
@Setter
public class Project {
  public enum Stato {
    IDEA,
    PIANIFICATO,
    IN_CORSO,
    IN_PAUSA,
    COMPLETATO,
    ANNULLATO
  }

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "uuid")
  private UUID id;

  @Column(name = "nome", nullable = false)
  private String nome;

  @Column(name = "descrizione")
  private String descrizione;

  @Enumerated(EnumType.STRING)
  @Column(name = "stato")
  private Stato stato = Stato.IDEA;

  @Column(name = "data_creazione", nullable = false)
  private LocalDateTime dataCreazione = LocalDateTime.now();

  @Column(name = "data_inizio_lavorazione")
  private LocalDateTime dataInizioLavorazione;

  @Column(name = "data_ultima_modifica", nullable = false)
  private LocalDateTime dataUltimaModifica = LocalDateTime.now();

  @Column(name = "data_scadenza", nullable = false)
  private LocalDateTime dataScadenza;

  @Column(name = "data_prevista_chiusura")
  private LocalDateTime dataPrevistaChiusura;

  @Column(name = "data_chiusura")
  private LocalDateTime dataChiusura;

  @OneToMany(mappedBy = "project", cascade = CascadeType.MERGE,
             orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Task> tasks = new ArrayList<>();

  @OneToMany(mappedBy = "project", cascade = CascadeType.MERGE,
             orphanRemoval = true, fetch = FetchType.LAZY)
  private List<ProjectMember> members = new ArrayList<>();

  @PreUpdate
  private void onPreUpdate() {
    dataUltimaModifica = LocalDateTime.now();
  }

  public int getTasksCount() {
    return tasks.size();
  }

  public int getMembersCount() {
    return members.size();
  }

  public void addTask(Task task) {
    if (tasks.contains(task)) return;
    task.setProject(this);
    tasks.add(task);
  }

  public void removeTask(Task task) {
    if (!tasks.contains(task)) return;
    tasks.remove(task);
    task.setProject(null);
  }

  public void addMember(ProjectMember member) {
    if (members.contains(member)) return;
    members.add(member);
    member.setProject(this);
  }

  public void removeMember(ProjectMember member) {
    if (!members.contains(member)) return;
    members.remove(member);
    member.setProject(null);
  }

  public boolean isChiuso() {
    return this.stato == Stato.COMPLETATO || this.stato == Stato.ANNULLATO;
  }

  public boolean isChiusoInRitardo() {
    return dataChiusura != null
           && dataScadenza != null
           && dataChiusura.isAfter(dataScadenza);
  }

  public boolean isChiusoOltrePrevisione() {
    return dataPrevistaChiusura != null
           && dataChiusura != null
           && dataPrevistaChiusura.isBefore(dataChiusura);
  }
}
