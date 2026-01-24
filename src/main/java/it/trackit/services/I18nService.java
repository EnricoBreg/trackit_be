package it.trackit.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class I18nService {
  private final MessageSource messageSource;

  public String getLocalizedMessage(String key, Locale locale) {
    return messageSource.getMessage(key, null, locale);
  }
}
