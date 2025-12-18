package it.trackit.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "project_members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMember {

  @EmbeddedId
  private ProjectMemberKey id;

  @ManyToOne
  @MapsId("projectId")
  @JoinColumn(name = "project_id")
  private Project project;

  @ManyToOne
  @MapsId("userId")
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "project_role_id", nullable = false)
  private ProjectRole role;
}
