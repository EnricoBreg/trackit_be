package it.trackit.config.i18n;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@Configuration
public class I18nConfiguration {

  @Bean
  public AcceptHeaderLocaleResolver localeResolver() {
    AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
    localeResolver.setDefaultLocale(Locale.ITALY); // Default Italian Locale
    return localeResolver;
  }

  @Bean
  public ResourceBundleMessageSource messageSource() {
    ResourceBundleMessageSource source = new ResourceBundleMessageSource();
    source.setBasenames("i18n/messages", "i18n/exceptions"); // nomi base dei resource bundles
    source.setDefaultEncoding("UTF-8");
    source.setUseCodeAsDefaultMessage(true);
    source.setCacheMillis(3600); // Cache di 1h
    return source;
  }
}
