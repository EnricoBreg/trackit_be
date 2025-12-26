package it.trackit.controllers;

import it.trackit.commons.exceptions.TaskNotFoundException;
import it.trackit.commons.exceptions.UserNotFoundException;
import it.trackit.dtos.projects.AssignTaskRequest;
import it.trackit.dtos.projects.TaskDto;
import it.trackit.dtos.projects.UpdateTaskInfoRequest;
import it.trackit.services.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/tasks")
@Validated // attiva la validazione sulla firma dei metodi
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
    taskService.deleteTask(taskId);
  }

  @PatchMapping("/{taskId}/assignee")
  public TaskDto assignTask(
    @PathVariable("taskId") Long taskId,
    @Valid @RequestBody AssignTaskRequest request
  ) {
    var userId = request.getUserId();
    return taskService.assignTask(taskId, userId);
  }

  @ExceptionHandler({TaskNotFoundException.class, UserNotFoundException.class})
  public ResponseEntity<Map<String, String>> handleTaskNotFound(Exception ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
  }
}
