package it.trackit.commons.exceptions;

import lombok.Getter;

import java.util.Map;

@Getter
public class UserExistsException extends RuntimeException {
  private Map<String, String> fieldErrors;

  public UserExistsException() {
    this("user.exists");
  }

  public UserExistsException(String message) {
    this(message, Map.of());
  }

  public UserExistsException(String message, Map<String, String> fieldErrors) {
    super(message);
    this.fieldErrors = fieldErrors;
  }
}
