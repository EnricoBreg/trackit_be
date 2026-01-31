package it.trackit.mappers;

import it.trackit.dtos.projects.ProjectRoleDto;
import it.trackit.entities.ProjectRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectRoleMapper {
  ProjectRoleDto toDto(ProjectRole projectRole);
}
