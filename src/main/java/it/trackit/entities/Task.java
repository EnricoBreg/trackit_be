package it.trackit.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "tasks")
@Getter
@Setter
public class Task {
  public enum Stato {
    PENDING,
    IN_PROGRESS,
    DONE,
    CANCELLED
  }

  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  private Long id;

  @Column(name = "titolo")
  private String titolo;

  @Column(name = "descrizione")
  private String descrizione;

  @Enumerated(EnumType.STRING)
  @Column(name = "stato")
  private Stato stato = Stato.PENDING;

  @Column(name = "priorita")
  private Integer priorita = 1;

  //private Project project;

  @ManyToOne
  @JoinColumn(name = "assignee_id")
  private User assegnatario;

  @ManyToOne
  @JoinColumn(name = "reporter_id")
  private User creatore;

  @ManyToOne
  @JoinColumn(name = "parent_task_id")
  private Task parentTask;
}
