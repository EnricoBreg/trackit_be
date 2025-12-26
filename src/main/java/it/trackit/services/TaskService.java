package it.trackit.services;

import it.trackit.commons.exceptions.TaskNotFoundException;
import it.trackit.commons.exceptions.UserNotFoundException;
import it.trackit.dtos.projects.TaskDto;
import it.trackit.dtos.projects.UpdateTaskInfoRequest;
import it.trackit.mappers.TaskMapper;
import it.trackit.repositories.TaskRepository;
import it.trackit.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;

  private final TaskMapper taskMapper;
  private final UserRepository userRepository;

  public TaskDto findTask(Long taskId) {
    var task = taskRepository.findById(taskId).orElse(null);
    return taskMapper.toDto(task);
  }

  public TaskDto updateTaskInfo(Long taskId, UpdateTaskInfoRequest request) {
    var task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
    taskMapper.update(request, task);

    taskRepository.save(task);

    return taskMapper.toDto(task);
  }

  public void deleteTask(Long taskId) {
    var task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
    taskRepository.delete(task);
  }

  public TaskDto assignTask(Long taskId, Long userId) {
    var task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
    var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

    task.setAssegnatario(user);
    taskRepository.save(task);

    return taskMapper.toDto(task);
  }
}
