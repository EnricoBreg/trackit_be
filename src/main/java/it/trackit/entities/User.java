package it.trackit.entities;

import it.trackit.config.security.permissions.global.GlobalRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  private Long id;

  @Column(name = "username")
  private String username;

  @Column(name = "nome")
  private String nome;

  @Column(name = "cognome")
  private String cognome;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "global_role", nullable = false)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private GlobalRole globalRole = GlobalRole.ROLE_USER;

  @Column(name = "is_active", nullable = false)
  @Builder.Default
  private Boolean isActive = true;

  public String getDisplayName() {
    String displayName = "";
    if (nome != null) {
      displayName = nome;
    }
    if (cognome != null) {
      displayName += " " + cognome;
    }
    displayName += " (" + getUsername() + ")";
    return displayName;
  }

  public boolean isSuperAdmin() {
    return globalRole == GlobalRole.ROLE_SUPER_ADMIN;
  }
}
