package it.trackit.mappers;

import it.trackit.dtos.projects.CreateProjectRequest;
import it.trackit.dtos.projects.ProjectDto;
import it.trackit.dtos.projects.ProjectMemberDto;
import it.trackit.entities.Project;
import it.trackit.entities.ProjectMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {TaskMapper.class})
public interface ProjectMapper {
  @Mapping(target = "tasks", source = "tasks")
  @Mapping(target = "members", source = "members")
  @Mapping(target = "tasksCount", expression = "java(project.getTasksCount())")
  @Mapping(target = "membersCount", expression = "java(project.getMembersCount())")
  ProjectDto toDto(Project project);

  Project toEntity(CreateProjectRequest dto);

  @Mapping(target = "role", source = "role.nome")
  @Mapping(target = "user.nominativo", expression = "java(user.getDisplayName())")
  @Mapping(target = "projectId", source = "project.id")
  ProjectMemberDto toDto(ProjectMember projectMember);
}
