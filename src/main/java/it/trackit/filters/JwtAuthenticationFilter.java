package it.trackit.filters;

import it.trackit.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final static String AUTH_HEADER = "Authorization";
  private final static String TOKEN_PREFIX = "Bearer ";

  private final JwtService jwtService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    var authHeader = request.getHeader(AUTH_HEADER);
    if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX)) {
      filterChain.doFilter(request, response);
      return;
    }

    var token = authHeader.replace(TOKEN_PREFIX, "");
    if (!jwtService.validateToken(token)) {
      filterChain.doFilter(request, response);
      return;
    }

    var userId = jwtService.getUserIdFromToken(token);
    var authorities = jwtService.getAuthoritiesFromToken(token);

    System.out.println("Authorities: " + (authorities.stream().map(a -> a.toString() + " ").toList()));

    var authentication = new UsernamePasswordAuthenticationToken(
      userId,
      null,
      authorities
    );
    authentication.setDetails(
      new WebAuthenticationDetailsSource().buildDetails(request)
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    filterChain.doFilter(request, response);
  }
}
