package it.trackit.mappers;

import it.trackit.dtos.ProjectDto;
import it.trackit.entities.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
  ProjectDto toDto(Project project);
}
