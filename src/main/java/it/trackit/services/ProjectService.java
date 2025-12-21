package it.trackit.services;

import it.trackit.commons.exceptions.ProjectNotFoundException;
import it.trackit.commons.exceptions.UserNotFoundException;
import it.trackit.dtos.projects.CreateProjectRequest;
import it.trackit.dtos.projects.CreateProjectTaskRequest;
import it.trackit.dtos.projects.ProjectDto;
import it.trackit.dtos.projects.TaskDto;
import it.trackit.mappers.ProjectMapper;
import it.trackit.mappers.TaskMapper;
import it.trackit.repositories.ProjectRepository;
import it.trackit.repositories.TaskRepository;
import it.trackit.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProjectService {

  private final ProjectRepository projectRepository;
  private final ProjectMapper projectMapper;
  private final TaskRepository taskRepository;
  private final TaskMapper taskMapper;
  private final UserService userService;
  private final UserRepository userRepository;


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

  public List<TaskDto> getProjectTasks(UUID projectId) {
    var tasks = taskRepository.findByProject_Id(projectId);
    return tasks.stream().map(taskMapper::toDto).toList();
  }

  public ProjectDto createProjectFromRequest(CreateProjectRequest request) {
    var newProject = projectMapper.toEntity(request);
    newProject.setDataCreazione(LocalDateTime.now());
    // newProject.setCreatore(utenteLoggato) // TODO: da implementare il creatore del progetto
    projectRepository.save(newProject);

    return projectMapper.toDto(newProject);
  }

  @Transactional
  public TaskDto createProjectTask(UUID projectId, CreateProjectTaskRequest request) {
    var project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
    var user = userRepository.findById(request.getAssegnatario()).orElseThrow(UserNotFoundException::new);

    var task = taskMapper.toEntity(request);
    project.addTask(task);
    task.setAssegnatario(user);
    task.setCreatore(user); // TODO: da implementare il creatore del progetto

    taskRepository.save(task);

    return taskMapper.toDto(task);
  }
}
