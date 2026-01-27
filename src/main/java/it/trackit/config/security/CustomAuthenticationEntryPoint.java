package it.trackit.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.trackit.commons.exceptions.ErrorDto;
import it.trackit.services.I18nService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;
  private final I18nService i18nService;

  @Override
  public void commence(HttpServletRequest request,
                       HttpServletResponse response,
                       AuthenticationException authException) throws IOException, ServletException {

    // Impostazione delle status 401
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    // Body della risposta
    ErrorDto payload = ErrorDto.builder()
      .error("AUTH_ERROR")
      .message(i18nService.getLocalizedString("unauthorized"))
      .timestamp(System.currentTimeMillis())
      .build();

    objectMapper.writeValue(response.getOutputStream(), payload);
  }
}
