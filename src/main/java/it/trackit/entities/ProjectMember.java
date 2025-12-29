package it.trackit.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "project_members")
@Getter
@Setter
@NoArgsConstructor
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

  public ProjectMember(Project project, User user, ProjectRole role) {
    this.id = new ProjectMemberKey();
    this.project = project;
    this.user = user;
    this.role = role;
  }
}
