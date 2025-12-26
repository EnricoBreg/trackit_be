package it.trackit.controllers;

import it.trackit.dtos.projects.TaskDto;
import it.trackit.dtos.projects.UpdateTaskInfoRequest;
import it.trackit.services.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

  private final TaskService taskService;

  @GetMapping("/{taskId}")
  public TaskDto getTask(@PathVariable("taskId") Long taskId) {
    return taskService.findTask(taskId);
  }

  @PutMapping("/{taskId}")
  public TaskDto updateTask(
    @PathVariable("taskId") Long taskId,
    @Valid @RequestBody UpdateTaskInfoRequest request
  ) {
    return taskService.updateTaskInfo(taskId, request);
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
