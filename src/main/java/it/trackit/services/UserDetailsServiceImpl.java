package it.trackit.services;

import it.trackit.commons.exceptions.UserNotFoundException;
import it.trackit.config.security.UserPrincipal;
import it.trackit.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = userRepository.findByUsername(username).orElseThrow(
      () -> new UsernameNotFoundException(
        "User not found with username: " + username,
        new UserNotFoundException("User not found with username: " + username))
    );

    var authorities = new ArrayList<SimpleGrantedAuthority>();
    authorities.add(new SimpleGrantedAuthority(user.getGlobalRole().name()));

    var principal = new UserPrincipal(user);
    principal.setAuthorities(authorities);

    return principal;
  }
}
