package it.trackit.repositories;

import it.trackit.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
  @EntityGraph(attributePaths = {"tasks.assegnatario", "tasks.creatore"})
  @Query("SELECT p FROM Project p WHERE p.id = :projectId")
  Optional<Project> getProjectWithTasks(@Param("projectId") UUID id);

  @Query("""
    SELECT p FROM Project p
    WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :searchText, '%'))
      OR LOWER(p.descrizione) LIKE LOWER(CONCAT('%', :searchText, '%'))
  """)
  Page<Project> searchProjects(@Param("searchText") String searchText, Pageable pageable);
}
