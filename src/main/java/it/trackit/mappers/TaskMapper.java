package it.trackit.mappers;

import it.trackit.dtos.projects.CreateProjectTaskRequest;
import it.trackit.dtos.projects.TaskDto;
import it.trackit.dtos.projects.TaskUserDto;
import it.trackit.dtos.projects.UpdateTaskInfoRequest;
import it.trackit.entities.Task;
import it.trackit.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TaskMapper {
  @Mapping(target = "id", source = "id")
  @Mapping(target = "projectId", source = "project.id")
  TaskDto toDto(Task task);

  @Mapping(target = "nominativo", expression = "java(user.getDisplayName())")
  TaskUserDto toDto(User user);

  @Mapping(ignore = true, target = "assegnatario")
  Task toEntity(CreateProjectTaskRequest dto);

  void update(UpdateTaskInfoRequest dto, @MappingTarget Task task);
}
