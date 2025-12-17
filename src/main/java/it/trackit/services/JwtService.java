package it.trackit.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.trackit.entities.User;
import it.trackit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

  private final UserRepository userRepository;
  @Value("${spring.jwt.secret}")
  private String secret;

  public JwtService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public String generateAccessToken(User user) {
    final long tokenExpiration = 300; // 5 min

    return generateAccessToken(user, tokenExpiration);
  }

  public String generateRefreshToken(User user) {
    final long tokenExpiration = 604800; // 7 days

    return generateAccessToken(user, tokenExpiration);
  }

  private String generateAccessToken(User user, long tokenExpiration) {
    return Jwts.builder()
      .subject(user.getId().toString())
      .claim("nome", user.getNome())
      .claim("email", user.getEmail())
      .issuedAt(new Date())
      .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
      .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
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

  private Claims getClaims(String token) {
    return Jwts.parser()
      .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
      .build()
      .parseSignedClaims(token)
      .getPayload();
  }
}
