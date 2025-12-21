package it.trackit.mappers;

import it.trackit.dtos.projects.CreateProjectRequest;
import it.trackit.dtos.projects.ProjectDto;
import it.trackit.entities.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TaskMapper.class})
public interface ProjectMapper {
  @Mapping(target = "tasks", source = "tasks")
  @Mapping(target = "tasksCount", expression = "java(project.getTasksCount())")
  ProjectDto toDto(Project project);

  Project toEntity(CreateProjectRequest dto);
}
