package it.trackit.controllers;

import it.trackit.commons.exceptions.ProjectNotFoundException;
import it.trackit.commons.exceptions.RoleNotFoundException;
import it.trackit.commons.exceptions.UserNotFoundException;
import it.trackit.dtos.PaginatedResponse;
import it.trackit.dtos.UserDto;
import it.trackit.dtos.projects.*;
import it.trackit.repositories.ProjectRepository;
import it.trackit.repositories.TaskRepository;
import it.trackit.services.ProjectService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
  public PaginatedResponse<ProjectDto> getProjects(
    @PageableDefault(size = 15, sort = {"nome"}) Pageable pageable,
    @RequestParam(name = "search", required = false) String searchText) {
    return projectService.getAllProjects(pageable, searchText);
  }

  @PostMapping
  public ResponseEntity<ProjectDto> createProject(
    @RequestBody CreateProjectRequest request,
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
  public ProjectDto getProject(
    @PathVariable("projectId") UUID projectId) {
    return projectService.getProject(projectId);
  }

  @GetMapping("/{projectId}/tasks")
  public PaginatedResponse<TaskDto> getProjectTasks(
    @PageableDefault(size = 15, sort = {"titolo"}) Pageable pageable,
    @PathVariable("projectId") UUID projectId
  ) {
    return projectService.getProjectTasks(pageable, projectId);
  }

  @PostMapping("/{projectId}/tasks")
  public TaskDto createProjectTask(
    @PathVariable("projectId") UUID projectId,
    @Valid @RequestBody CreateProjectTaskRequest request
  ) {
    return projectService.createProjectTask(projectId, request);
  }

  @GetMapping("/{projectId}/members")
  public PaginatedResponse<UserDto> getProjectMembers(
    @PageableDefault(size = 15, sort = {"user.cognome"}) Pageable pageable,
    @PathVariable("projectId") UUID projectId
  ) {
    return projectService.getProjectMembers(pageable, projectId);
  }

  @PostMapping("/{projectId}/members")
  public ResponseEntity<ProjectMemberDto> addProjectMember(
    @PathVariable("projectId") UUID projectId,
    @Valid @RequestBody AddProjectMemberRequest request,
    UriComponentsBuilder uriBuilder
  ) {
    var userId = request.getUserId();
    var roleName = request.getRole();

    var projectMemberDto = projectService.addUserWithRole(projectId, userId, roleName);
    var uri = uriBuilder.path("/api/projects/{id}").buildAndExpand(projectMemberDto.getProjectId()).toUri();

    return ResponseEntity.created(uri).body(projectMemberDto);
  }

  @DeleteMapping("/{projectId}/members/{userId}")
  public ResponseEntity<Void> removeProjectMember(
    @PathVariable("projectId") UUID projectId,
    @PathVariable("userId") Long userId
  ) {
    projectService.removeMember(projectId, userId);
    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler({ProjectNotFoundException.class, RoleNotFoundException.class, UserNotFoundException.class})
  public ResponseEntity<Map<String, String>> handleProjectNotFound(Exception ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
  }
}
