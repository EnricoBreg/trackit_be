package it.trackit.dtos.projects;

import lombok.Data;

@Data
public class ProjectRoleDto {
  private Long id;
  private String nome;
  private String displayName;
  private Integer livello = 1;
}
