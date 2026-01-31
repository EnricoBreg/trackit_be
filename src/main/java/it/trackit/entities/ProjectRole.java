package it.trackit.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "project_roles")
@Getter
@Setter
public class ProjectRole {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "nome_ruolo", nullable = false)
  private String nome;

  @Column(name = "display_name", nullable = false)
  private String displayName;

  @Column(name = "level", nullable = false)
  private Integer livello = 1;
}
