package it.trackit.controllers;

import it.trackit.dtos.ProjectDto;
import it.trackit.repositories.ProjectRepository;
import it.trackit.repositories.TaskRepository;
import it.trackit.services.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {

  private final ProjectService projectService;
  private final ProjectRepository projectRepository;
  private final TaskRepository taskRepository;

  /**
   * Endpoint GET per avere tutti i progetti.
   * @return lista di {@link ProjectDto}
   */
  @GetMapping
  public List<ProjectDto> getProjects() {
    return projectService.getAllProjects();
  }

  /**
   * Endpoint GET per avere uno specifico progetto
   * @param projectId UUID che indentifica univocamente il progetto
   * @return istanza di {@link ProjectDto} se esiste il progetto, altrimenti errore 404
   */
  @GetMapping("/{projectId}")
  public ProjectDto getProject(@PathVariable("projectId") UUID projectId) {
    return projectService.getProject(projectId);
  }

//  @GetMapping("/{projectId}/tasks")
//  public ResponseEntity<?> getProjectTasks(@PathVariable("projectId") UUID projectId) {
//    return projectService.getProject(projectId);
//  }
}
