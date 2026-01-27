package it.trackit.commons.exceptions;

public class ForbiddenException extends RuntimeException {

  public ForbiddenException() {
    super("forbidden");
  }

  public ForbiddenException(String message) {
    super(message);
  }
}
