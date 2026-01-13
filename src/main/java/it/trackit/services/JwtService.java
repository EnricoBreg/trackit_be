package it.trackit.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import it.trackit.config.JwtConfig;
import it.trackit.entities.User;
import it.trackit.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class JwtService {

  private final static String ROLE_CLAIM_TAG = "global_role";

  private final UserRepository userRepository;
  private final JwtConfig jwtConfig;

  public String generateAccessToken(User user) {
    return generateToken(user, jwtConfig.getAccessTokenExpiration());
  }

  public String generateRefreshToken(User user) {
    return generateToken(user, jwtConfig.getRefreshTokenExpiration());
  }

  private String generateToken(User user, long tokenExpiration) {
    return Jwts.builder()
      .subject(user.getId().toString())
      .claim("nome", user.getNome())
      .claim("email", user.getEmail())
      .claim(ROLE_CLAIM_TAG, user.getGlobalRole().name())
      .issuedAt(new Date())
      .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
      .signWith(jwtConfig.getSecretKey())
      .compact();
  }

  public Boolean validateToken(String token) {
    try {
      var claims = getClaims(token);
      return claims.getExpiration().after(new Date());

    } catch (JwtException e) {
      return false;
    }
  }

  public Long getUserIdFromToken(String token) {
    return Long.valueOf(getClaims(token).getSubject());
  }

  public List<SimpleGrantedAuthority> getAuthoritiesFromToken(String token) {
    String role = (String)getClaims(token).get(ROLE_CLAIM_TAG);
    return role != null ? List.of(new SimpleGrantedAuthority(role)) : List.of();
  }

  private Claims getClaims(String token) {
    return Jwts.parser()
      .verifyWith(jwtConfig.getSecretKey())
      .build()
      .parseSignedClaims(token)
      .getPayload();
  }
}
