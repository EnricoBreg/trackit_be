package it.trackit.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class I18nService {
  private final MessageSource messageSource;

  public String getLocalizedString(String key, Locale locale) {
    return getLocalizedString(key, locale, (Object) null);
  }

  public String getLocalizedString(String key, Object... args) {
    Locale locale = LocaleContextHolder.getLocale();
    return getLocalizedString(key, locale, args);
  }

  public String getLocalizedString(String key) {
    Locale locale = LocaleContextHolder.getLocale();
    return getLocalizedString(key, locale);
  }

  public String getLocalizedString(String key, Locale locale, Object... args) {
    return messageSource.getMessage(key, args, locale);
  }
}
