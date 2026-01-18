package it.trackit.commons.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorDto> handleUnreadableMessage(HttpMessageNotReadableException exception) {
    return ResponseEntity.badRequest().body(
      ErrorDto.builder()
        .error("Invalid request body")
        .message(exception.getMessage())
        .timestamp(System.currentTimeMillis())
        .build()
    );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorDto> handleValidationErrors(
    MethodArgumentNotValidException exception
  ) {
    var errors = new HashMap<String, String>();

    exception.getBindingResult().getFieldErrors().forEach(e -> {
      errors.put(e.getField(), e.getDefaultMessage());
    });

    var error = ErrorDto.builder()
      .error("VALIDATION_ERROR")
      .message("Validation error")
      .errors(errors)
      .timestamp(System.currentTimeMillis())
      .build();

    return ResponseEntity.badRequest().body(error);
  }

  /*
   * Questo exception handler viene usato per la validazione fatta da Spring per gli
   * argomenti dei metodi dei controller. In alcuni casi, risulta utile utilizzare
   * questa validazione invece che passare per un DTO per facilitare anche la
   * scrittura del codice. Comunque utilizzare DTO rimane sempre la forma
   * preferibile anche per manutenibilità e comprensione del codice.
   * ATTENZIONE: per far si che Spring avvii questo tipo di
   * validazione, bisogna annotare il controller con
   * l'annotazione @Validated.
   */
  @ExceptionHandler({ConstraintViolationException.class})
  public ResponseEntity<ErrorDto> handleConstraintViolation(ConstraintViolationException exception) {
    Map<String, String> errors = new HashMap<>();

    exception.getConstraintViolations().forEach(violation -> {
      String propertyPath = violation.getPropertyPath().toString();
      String fieldName = propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
      String message = violation.getMessage();

      errors.put(fieldName, message != null ? message : "Invalid value");
    });

    var error = ErrorDto.builder()
      .error("VALIDATION_ERROR")
      .message("Validation error")
      .errors(errors)
      .timestamp(System.currentTimeMillis())
      .build();

    return ResponseEntity.badRequest().body(error);
  }
}
