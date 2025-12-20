package it.trackit.commons.exceptions;

public class ProjectNotFoundException extends RuntimeException {
  public ProjectNotFoundException() {
    super("Project not found");
  }
}
