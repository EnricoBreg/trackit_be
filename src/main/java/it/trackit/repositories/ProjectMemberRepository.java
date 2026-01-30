package it.trackit.repositories;

import it.trackit.entities.ProjectMember;
import it.trackit.entities.ProjectMemberKey;
import it.trackit.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberKey> {

  // Trova tutti i progetti di un utente
  //  Spring legge così:
  //  findBy...
  //  User: Cerca il campo user dentro ProjectMember. (Lo trova! È la @ManyToOne).
  //  _: Entra dentro l'oggetto User.
  //  Id: Cerca il campo userId dentro l'oggetto User. (Lo trova!).
  List<ProjectMember> findByUser_Id(Long userId);

  // Trova un membro specifico (per vedere il ruolo)
  // Spring capisce automaticamente come cercare dentro la chiave composta
  Optional<ProjectMember> findByProject_IdAndUserId(UUID projectId, Long userId);

  // Seleziona l'oggetto 'user' dentro l'entità ProjectMember
  // dove l'id del progetto corrisponde al parametro. Serve
  // per trovare gli utenti che sono membri di un progetto
  @Query("SELECT pm.user FROM ProjectMember pm WHERE pm.project.id = :projectId")
  Page<User> findUsersByProjectId(@Param("projectId") UUID id, Pageable pageable);

  boolean existsByProject_IdAndUser_Id(UUID projectId, Long userId);
}
