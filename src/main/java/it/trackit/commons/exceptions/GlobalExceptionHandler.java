package it.trackit.commons.exceptions;

import it.trackit.services.I18nService;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  private final I18nService i18nService;

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorDto> handleUnreadableMessage(HttpMessageNotReadableException exception) {
    log.error("GlobalExceptionHandler.handleUnreadableMessage: {}", exception.getMessage());

    return ResponseEntity.badRequest().body(
      ErrorDto.builder()
        .error("INVALID_REQUEST_BODY")
        .message(i18nService.getLocalizedString("http.invalidRequestBody"))
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
      errors.put(e.getField(), i18nService.getLocalizedString(e.getDefaultMessage()));
    });

    var error = ErrorDto.builder()
      .error("VALIDATION_ERROR")
      .message(i18nService.getLocalizedString("validationError"))
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
      String message = i18nService.getLocalizedString(violation.getMessage());

      errors.put(fieldName, message != null ? message : i18nService.getLocalizedString("invalidValue"));
    });

    var error = ErrorDto.builder()
      .error("VALIDATION_ERROR")
      .message(i18nService.getLocalizedString("validationError"))
      .errors(errors)
      .timestamp(System.currentTimeMillis())
      .build();

    return ResponseEntity.badRequest().body(error);
  }
}
