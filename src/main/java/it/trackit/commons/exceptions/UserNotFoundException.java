package it.trackit.commons.exceptions;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException() {
    super("user.notFound");
  }

  public UserNotFoundException(String message) {
    super(message);
  }
}
