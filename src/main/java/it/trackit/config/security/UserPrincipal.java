package it.trackit.config.security;

import it.trackit.entities.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {
  private final User user;
  private List<? extends GrantedAuthority> authorities = List.of();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return user.getActive();
  }

  @Override
  public boolean isAccountNonLocked() {
    return user.getActive();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return user.getActive();
  }

  @Override
  public boolean isEnabled() {
    return user.getActive();
  }
}
