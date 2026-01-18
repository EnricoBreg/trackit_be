package it.trackit.commons.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {
  private String error;
  private String message;
  private long timestamp;
  private Map<String, String> errors;
}


