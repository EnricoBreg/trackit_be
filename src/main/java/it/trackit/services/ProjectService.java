package it.trackit.services;

import it.trackit.commons.exceptions.ProjectNotFoundException;
import it.trackit.commons.exceptions.RoleNotFoundException;
import it.trackit.commons.exceptions.UserNotFoundException;
import it.trackit.commons.utils.DomainUtils;
import it.trackit.config.security.UserPrincipal;
import it.trackit.dtos.PaginatedResponse;
import it.trackit.dtos.projects.*;
import it.trackit.entities.*;
import it.trackit.mappers.*;
import it.trackit.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
  private final ProjectMemberMapper projectMemberMapper;
  private final ProjectRoleMapper projectRoleMapper;


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

  public PaginatedResponse<TaskDto> getProjectTasks(Pageable pageable, UUID projectId) {
    Page<Task> page = taskRepository.findByProject_Id(projectId, pageable);
    var tasks = page.getContent().stream().map(taskMapper::toDto).toList();

    return DomainUtils.buildPaginatedResponse(page, tasks);
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
    var assegnatario = request.getAssegnatario() != null
      ? userRepository.findById(request.getAssegnatario()).orElseThrow(UserNotFoundException::new)
      : null;

    UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User reporter = null;
    if (principal != null) {
      reporter = principal.getUser();
    }

    var task = taskMapper.toEntity(request);
    project.addTask(task);
    task.setAssegnatario(assegnatario);
    task.setReporter(reporter);

    taskRepository.save(task);

    return taskMapper.toDto(task);
  }

  public PaginatedResponse<ProjectMemberDto> getProjectMembers(Pageable pageable, UUID projectId) {
    Page<ProjectMember> page = projectMemberRepository.findByProject_Id(projectId, pageable);
    var projectMembersDto = page.getContent().stream().map(projectMemberMapper::toDto).toList();

    return DomainUtils.buildPaginatedResponse(page, projectMembersDto);
  }

  public ProjectMemberDto addUserWithRole(UUID projectId, Long userId, Long roleId) throws RoleNotFoundException {
    var project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
    var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    var role = projectRoleRepository.findById(roleId).orElseThrow(RoleNotFoundException::new);

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

  public PaginatedResponse<ProjectRoleDto> getAllRoles(Pageable pageable, String searchText) {
    Page<ProjectRole> page = searchText != null ? projectRoleRepository.findByNomeContainingIgnoreCase(searchText, pageable)
                                                : projectRoleRepository.findAll(pageable);

    var roles = page.getContent().stream().map(projectRoleMapper::toDto).toList();
    return DomainUtils.buildPaginatedResponse(page, roles);
  }
}
