package it.trackit.commons.exceptions;

public class TaskNotFoundException extends RuntimeException {
  public TaskNotFoundException() {
    super("task.notFound");
  }
}
