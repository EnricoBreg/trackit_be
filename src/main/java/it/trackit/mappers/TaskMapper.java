package it.trackit.mappers;

import it.trackit.dtos.projects.TaskDto;
import it.trackit.dtos.projects.TaskUserDto;
import it.trackit.entities.Task;
import it.trackit.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
  TaskDto toDto(Task task);

  @Mapping(target = "nominativo", expression = "java(user.getDisplayName())")
  TaskUserDto toDto(User user);
}
