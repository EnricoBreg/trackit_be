package it.trackit.controllers;

import it.trackit.dtos.projects.TaskDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

  @GetMapping("/{taskId}")
  public TaskDto getTask(@PathVariable("taskId") Long taskId) {
    throw new UnsupportedOperationException();
  }

  @PutMapping("/{taskId}")
  public TaskDto updateTask(@PathVariable("taskId") Long taskId, @RequestBody TaskDto taskDto) {
    throw new UnsupportedOperationException();
  }

  @DeleteMapping("/{taskId}")
  public void deleteTask(@PathVariable("taskId") Long taskId) {
    throw new UnsupportedOperationException();
  }

  @PatchMapping("/{taskId}/assignee")
  public TaskDto assignTask(@PathVariable("taskId") Long taskId, @RequestBody Long userId) {
    throw new UnsupportedOperationException();
  }
}
