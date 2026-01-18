package it.trackit.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.trackit.commons.exceptions.ErrorDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  private final ObjectMapper objectMapper;

  @Override
  public void handle(HttpServletRequest request,
                     HttpServletResponse response,
                     AccessDeniedException accessDeniedException) throws IOException, ServletException {

    // Impostazione delle status 403
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    // Corpo della risposta
    ErrorDto payload = ErrorDto.builder()
      .error(HttpStatus.FORBIDDEN.getReasonPhrase())
      .message("Non hai i permessi per eseguire questa azione o il token è mancante.")
      .timestamp(System.currentTimeMillis())
      .build();

    objectMapper.writeValue(response.getOutputStream(), payload);
  }
}
