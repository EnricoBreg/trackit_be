package it.trackit.repositories;

import it.trackit.entities.ProjectRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRoleRepository extends JpaRepository<ProjectRole, Long> {
  Optional<ProjectRole> findByNome(String nome);
}
