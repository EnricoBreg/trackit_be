package it.trackit.commons.exceptions;

public class RoleNotFound extends RuntimeException {
  public RoleNotFound() {
    super("Role not found");
  }
}
