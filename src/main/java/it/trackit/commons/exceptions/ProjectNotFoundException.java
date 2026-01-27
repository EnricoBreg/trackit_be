package it.trackit.commons.exceptions;

public class ProjectNotFoundException extends RuntimeException {
  public ProjectNotFoundException() {
    this("project.notFound");
  }

  public ProjectNotFoundException(String message) {
    super(message);
  }
}
