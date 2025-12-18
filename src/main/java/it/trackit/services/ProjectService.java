package it.trackit.services;

import it.trackit.dtos.ProjectDto;
import it.trackit.mappers.ProjectMapper;
import it.trackit.repositories.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService {

  private final ProjectRepository projectRepository;
  private final ProjectMapper projectMapper;


  public List<ProjectDto> getAllProjects() {
    var projects = projectRepository.findAll();
    return projects.stream().map(projectMapper::toDto).toList();
  }
}
