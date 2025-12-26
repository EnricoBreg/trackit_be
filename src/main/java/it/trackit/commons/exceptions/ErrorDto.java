package it.trackit.commons.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class ErrorDto {
  private String error;
  private String message;
}

