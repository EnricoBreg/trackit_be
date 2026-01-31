package it.trackit.mappers;

import it.trackit.dtos.projects.ProjectMemberDto;
import it.trackit.entities.ProjectMember;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {UserMapper.class, ProjectRoleMapper.class})
public interface ProjectMemberMapper {
  ProjectMemberDto toDto(ProjectMember projectMember);
}
