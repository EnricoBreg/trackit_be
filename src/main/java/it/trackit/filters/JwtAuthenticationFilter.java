package it.trackit.filters;

import it.trackit.config.security.UserPrincipal;
import it.trackit.services.JwtService;
import it.trackit.services.UserDetailsServiceImpl;
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
  private final UserDetailsServiceImpl userDetailsService;

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
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

    var username = jwtService.getUsernameFromToken(token);
    UserPrincipal userDetails = (UserPrincipal) userDetailsService.loadUserByUsername(username);

    var authentication = new UsernamePasswordAuthenticationToken(
      userDetails,
      null,
      userDetails.getAuthorities()
    );

    authentication.setDetails(
      new WebAuthenticationDetailsSource().buildDetails(request)
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    filterChain.doFilter(request, response);
  }
}
