package it.trackit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Value("${app.cors.allowed-origins}")
  private List<String> allowedOrigins;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // Stateless sessions (token-based authetication)
    // Disable CSRF
    // Authorize http request or not
    http
      .sessionManagement(c ->
          c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .csrf(AbstractHttpConfigurer::disable)
      .cors(cors -> cors.configurationSource(corsConfigurationSource()))
      .authorizeHttpRequests(c -> c
          .requestMatchers("/api/auth/login").permitAll()
          .requestMatchers(HttpMethod.POST,"/api/users").permitAll()
          .anyRequest().authenticated()
        );

    return http.build();
  }

  /*
   * Definizione delle regole CORS
   */
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    // Origini permesse (in dev si può usare localhost, in produzione il dominio vero)
    configuration.setAllowedOrigins(allowedOrigins);

    // Metodi permessi
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

    // Header permessi
    configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

    // (Opzionale) Esposizione di header specifici per il frontend
    configuration.addExposedHeader("Authorization");

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    // applicazione delle regole a tutti gli endpoint
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }
}
