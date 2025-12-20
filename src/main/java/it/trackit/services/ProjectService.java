package it.trackit.services;

import it.trackit.commons.exceptions.ProjectNotFoundException;
import it.trackit.dtos.projects.NewProjectRequest;
import it.trackit.dtos.projects.ProjectDto;
import it.trackit.entities.Task;
import it.trackit.mappers.ProjectMapper;
import it.trackit.repositories.ProjectRepository;
import it.trackit.repositories.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProjectService {

  private final ProjectRepository projectRepository;
  private final ProjectMapper projectMapper;
  private final TaskRepository taskRepository;


  public List<ProjectDto> getAllProjects() {
    var projects = projectRepository.findAll();
    return projects.stream().map(projectMapper::toDto).toList();
  }

  public ProjectDto getProject(UUID projectId) {
    var project = projectRepository.getProjectWithTasks(projectId).orElse(null);
    if (project == null) {
      throw new ProjectNotFoundException();
    }

    return projectMapper.toDto(project);
  }

  public List<Task> getProjectTasks(UUID projectId) {
    return taskRepository.findByProjectId(projectId);
  }

  public ProjectDto createProjectFromRequest(NewProjectRequest request) {
    var newProject = projectMapper.toEntity(request);
    newProject.setDataCreazione(LocalDateTime.now());
    // newProject.setCreatore(utenteLoggato) // TODO: da implementare il creatore del progetto
    projectRepository.save(newProject);

    return projectMapper.toDto(newProject);
  }
}
