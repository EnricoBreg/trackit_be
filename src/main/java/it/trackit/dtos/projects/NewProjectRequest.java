package it.trackit.dtos.projects;

import it.trackit.entities.Project;
import lombok.Data;

@Data
public class NewProjectRequest {
  private String nome;
  private String descrizione;
  private Project.Stato stato;
}
