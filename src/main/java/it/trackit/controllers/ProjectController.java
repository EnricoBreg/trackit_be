package it.trackit.controllers;

import it.trackit.commons.exceptions.ProjectNotFoundException;
import it.trackit.dtos.projects.NewProjectRequest;
import it.trackit.dtos.projects.ProjectDto;
import it.trackit.repositories.ProjectRepository;
import it.trackit.repositories.TaskRepository;
import it.trackit.services.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
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

  @PostMapping
  public ResponseEntity<ProjectDto> createProject(
    @RequestBody NewProjectRequest request,
    UriComponentsBuilder uriBuilder
  ) {
    var projectDto = projectService.createProjectFromRequest(request);

    var uri = uriBuilder.path("/api/projects/{id}").buildAndExpand(projectDto.getId()).toUri();

    return ResponseEntity.created(uri).body(projectDto);
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

  @ExceptionHandler({ProjectNotFoundException.class})
  public ResponseEntity<Map<String, String>> handleProjectNotFound() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Project not found."));
  }
}
