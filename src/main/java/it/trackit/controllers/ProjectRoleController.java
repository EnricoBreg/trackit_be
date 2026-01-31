package it.trackit.controllers;

import it.trackit.dtos.PaginatedResponse;
import it.trackit.dtos.projects.ProjectRoleDto;
import it.trackit.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
@Validated
public class ProjectRoleController {

  private final ProjectService projectService;

  @GetMapping
  @PreAuthorize("isAuthenticated()")
  public PaginatedResponse<ProjectRoleDto> getAllRoles(
    @PageableDefault(size = 15, sort = {"nome"}) Pageable pageable,
    @RequestParam(name = "search", required = false) String searchText
  ) {
    return projectService.getAllRoles(pageable, searchText);
  }
}
