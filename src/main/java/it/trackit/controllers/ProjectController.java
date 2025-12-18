package it.trackit.controllers;

import it.trackit.dtos.ProjectDto;
import it.trackit.services.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {

  private final ProjectService projectService;

  @GetMapping
  public List<ProjectDto> getProjects() {
    return projectService.getAllProjects();
  }
}
