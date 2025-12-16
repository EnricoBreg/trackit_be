package it.trackit.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

  public String generateToken(String username) {
    final long tokenExpiration = 86400; // 1 day

    return Jwts.builder()
      .subject(username)
      .issuedAt(new Date())
      .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
      .signWith(Keys.hmacShaKeyFor("secret".getBytes()))
      .compact();
  }
}
