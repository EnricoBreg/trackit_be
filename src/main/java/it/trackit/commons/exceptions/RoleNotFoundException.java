package it.trackit.commons.exceptions;

public class RoleNotFoundException extends RuntimeException {
  public RoleNotFoundException() {
    this("role.notFound");
  }

  public RoleNotFoundException(String message) {
    super(message);
  }
}
