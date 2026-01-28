package it.trackit.services;

import it.trackit.commons.exceptions.ProjectNotFoundException;
import it.trackit.commons.exceptions.RoleNotFoundException;
import it.trackit.commons.exceptions.UserNotFoundException;
import it.trackit.commons.utils.DomainUtils;
import it.trackit.dtos.PaginatedResponse;
import it.trackit.dtos.UserDto;
import it.trackit.dtos.projects.*;
import it.trackit.entities.Project;
import it.trackit.entities.ProjectMember;
import it.trackit.entities.ProjectMemberKey;
import it.trackit.mappers.ProjectMapper;
import it.trackit.mappers.TaskMapper;
import it.trackit.mappers.UserMapper;
import it.trackit.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProjectService {

  private final ProjectRepository projectRepository;
  private final TaskRepository taskRepository;
  private final UserRepository userRepository;
  private final ProjectMemberRepository projectMemberRepository;
  private final ProjectRoleRepository projectRoleRepository;

  private final ProjectMapper projectMapper;
  private final TaskMapper taskMapper;

  private final UserService userService;
  private final UserMapper userMapper;


  public PaginatedResponse<ProjectDto> getAllProjects(Pageable pageable, String searchText) {
    Page<Project> page = searchText != null ? projectRepository.searchProjects(searchText, pageable)
                                            : projectRepository.findAll(pageable);

    var projects = page.getContent().stream().map(projectMapper::toDto).toList();
    return DomainUtils.buildPaginatedResponse(page, projects);
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
    task.setReporter(user); // TODO: da implementare il creatore del progetto

    taskRepository.save(task);

    return taskMapper.toDto(task);
  }

  public List<UserDto> getProjectMembers(UUID projectId) {
    var users = projectMemberRepository.findUsersByProjectId(projectId);
    return users.stream().map(userMapper::toDto).toList();
  }

  public ProjectMemberDto addUserWithRole(UUID projectId, Long userId, String roleName) throws RoleNotFoundException {
    var project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
    var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    var role = projectRoleRepository.findByNome(roleName).orElseThrow(RoleNotFoundException::new);

    var projectMember = new ProjectMember(project, user, role);

    projectMemberRepository.save(projectMember);

    return projectMapper.toDto(projectMember);
  }

  public void removeMember(UUID projectId, Long userId) {
    var key = new ProjectMemberKey(projectId, userId);
    if (!projectMemberRepository.existsById(key)) {
      throw new UserNotFoundException("L'utente non è membro del progetto");
    }

    var projectMember = projectMemberRepository.findById(key).orElseThrow(UserNotFoundException::new);
    var project = projectMember.getProject();

    project.removeMember(projectMember);
    // NON serve chiamare projectMemberRepository.delete()!
    // Quando si salva 'project' (o al commit della transazione),
    // Hibernate vede che un elemento è stato rimosso dalla lista
    // e lancia la DELETE SQL automaticamente grazie a orphanRemoval=true.
    // projectMemberRepository.delete(projectMember);
    projectRepository.save(project);
  }
}
